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

import com.leandroinacio.picmeapi.user.IUserRepository;
import com.leandroinacio.picmeapi.user.User;
import com.leandroinacio.picmeapi.utils.FileUtils;

@Service
public class FaceService implements IFaceService {

	private static final Logger log = LoggerFactory.getLogger(FaceService.class);
	
	@Autowired
	private IFaceRepository faceRespository;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	public Face upload(MultipartFile file) throws IOException {
		
		// TODO: Get the user sending the file
		User user = userRepository.findById((long)1);
		
		// TODO: Double check face values
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

	public Page<Face> findByUser(User user, Integer page, Integer size) {
		return this.faceRespository.findByUser(user, PageRequest.of(page, size));
	}
	
	public Face serveOneImageById(Long id) {
		Face face = this.faceRespository.findById(id); //TODO: if null return 
		String filePath = FileUtils.getUserFacePath(face.getUser()) + 
				FileUtils.getFileName(face.getId(), face.getFileType());
		face.setFile(resourceLoader.getResource("file:" + filePath));
		return face;
	}

	public void deleteImage(Long id) throws IOException {
		Face face = this.faceRespository.findById(id);
		this.faceRespository.deleteById(face.getId());
		Files.deleteIfExists(Paths.get(FileUtils.getUserFacePath(face.getUser()), 
				FileUtils.getFileName(face.getId(), face.getFileType())));
	}
	
	public void train(Long userId) {
			
		// Setup user to fetch and face to get folder
		User user = new User() {{ setId(userId); }};
		Face face = this.faceRespository.findByUser(user, PageRequest.of(0, 50)).getContent().get(0);
		
		// Setup folder and get files
		File folder = new File(FileUtils.getUserFacePath(face.getUser()));
		FilenameFilter nameFilter = FileUtils.getDefaultFilenameFilter();
		File[] files = folder.listFiles(nameFilter);
			
		// Setup info on javacv analyzer
		MatVector faces = new MatVector(files.length);
		Mat labels = new Mat(files.length, 1, opencv_core.CV_32SC1);
		IntBuffer labelBuffer = labels.createBuffer();
		
		// Iterate images and prepare for analyze
		for (Integer pos = 0; pos < files.length; pos++) {
			File file = files[pos];
			
			// Get just the face
			Mat faceToAnalyze = FileUtils.detectFace(file).get(0);
			
			faces.put(pos, faceToAnalyze);
			labelBuffer.put(pos);
		}

		// Train facial recognition and save files
		String folderName = folder.getAbsolutePath() + "/";
        FaceRecognizer eigenfaces = EigenFaceRecognizer.create();
        FaceRecognizer fisherfaces = FisherFaceRecognizer.create();
        FaceRecognizer lbph = LBPHFaceRecognizer.create();
        eigenfaces.train(faces, labels);
        eigenfaces.save(folderName + "eigen.yml");
        fisherfaces.train(faces, labels);
        fisherfaces.save(folderName + "fisher.yml");
        lbph.train(faces, labels);
        lbph.save(folderName + "lbph.yml");
	}

}
