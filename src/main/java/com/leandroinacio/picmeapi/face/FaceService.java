package com.leandroinacio.picmeapi.face;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_face.EigenFaceRecognizer;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.bytedeco.javacpp.opencv_face.FisherFaceRecognizer;
import org.bytedeco.javacpp.opencv_face.LBPHFaceRecognizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.leandroinacio.picmeapi.jwt.JwtUser;
import com.leandroinacio.picmeapi.user.IUserRepository;
import com.leandroinacio.picmeapi.user.User;
import com.leandroinacio.picmeapi.utils.FileUtils;

@Service
public class FaceService implements IFaceService {

	private static final Logger log = LoggerFactory.getLogger(FaceService.class);
	
	@Autowired
	private IFaceRepository faceRespository;
		
	@Autowired
	private ResourceLoader resourceLoader;
	
	public Face upload(MultipartFile file, JwtUser jwtUser) throws IOException {
		
		// Set the user
		User user = new User(jwtUser);
		
		// Create face object and save to the db
		Face face = new Face();
		face.setUser(user);
		face.setFileType(file.getContentType());
		face = this.faceRespository.save(face);
				
		// Copy to folder and rename it
		FileUtils.copyToFolderAndRenameFile(file, FileUtils.getUserFacePath(face.getUser()), 
				FileUtils.getFileName(face.getId(), face.getFileType()));
		
		return face;
	}

	public Page<Face> findAll(Integer page, Integer size) {
		return this.faceRespository.findAll(PageRequest.of(page, size));
	}

	public Page<Face> findByUser(JwtUser jwtUser, Integer page, Integer size) {
		return this.faceRespository.findByUser(new User(jwtUser), PageRequest.of(page, size));
	}
	
	public Face serveOneImageById(Long id, JwtUser jwtUser) {
		Face face = this.faceRespository.findById(id);
		
		// Returns only if user matches the user requesting file
		if (face.getUser().getId() == jwtUser.getId()) {
			String filePath = FileUtils.getUserFacePath(face.getUser()) + 
					FileUtils.getFileName(face.getId(), face.getFileType());
			face.setFile(resourceLoader.getResource("file:" + filePath));
		}
		return face;
	}

	public void deleteImage(Long id, JwtUser jwtUser) throws IOException {
		Face face = this.faceRespository.findById(id);
		
		// Remove only if face user matches the user on request
		if (jwtUser.getId() == face.getUser().getId()) {
			this.faceRespository.deleteById(face.getId());
			Files.deleteIfExists(Paths.get(FileUtils.getUserFacePath(face.getUser()), 
					FileUtils.getFileName(face.getId(), face.getFileType())));
		}
	}
	
	public void train(JwtUser jwtUser) {
			
		// Setup user to get folder
		User user = new User(jwtUser);
		
		// Setup folder and get files
		File folder = new File(FileUtils.getUserFacePath(user));
		FilenameFilter nameFilter = FileUtils.getDefaultFilenameFilter();
		File[] files = folder.listFiles(nameFilter);
		
		// Setup folder name and facial recognition methods
		String folderName = folder.getAbsolutePath() + "/";
        FaceRecognizer eigenfaces = EigenFaceRecognizer.create();
        FaceRecognizer fisherfaces = FisherFaceRecognizer.create();
        FaceRecognizer lbph = LBPHFaceRecognizer.create();
		
		// Setup info on javacv analyzer
		MatVector faces = new MatVector(files.length);
		Mat labels = new Mat(files.length, 1, opencv_core.CV_32SC1);
		IntBuffer labelBuffer = labels.createBuffer();
		
		// Iterate images and prepare Map to analyze
		for (Integer pos = 0; pos < files.length; pos++) {
						
			File file = files[pos];
			
			// Get just the face - Should have only one since it's training
			Mat faceToAnalyze = FileUtils.detectFace(file).get(0);
			
			// Add faces found and user id to label
			faces.put(pos, faceToAnalyze);
			
			// TODO: Think about better solution.
			// Should be the user ID, but it doesn't accept long values
			// And it does not accept just one label per analize
			labelBuffer.put(pos);
		}

		// Train facial recognition and save files
		eigenfaces.train(faces, labels);
        eigenfaces.save(folderName + "eigen.yml");
        fisherfaces.train(faces, labels);
        fisherfaces.save(folderName + "fisher.yml");
        lbph.train(faces, labels);
        lbph.save(folderName + "lbph.yml");
	}

}
