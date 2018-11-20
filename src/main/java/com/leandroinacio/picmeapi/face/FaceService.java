package com.leandroinacio.picmeapi.face;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_face.EigenFaceRecognizer;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.bytedeco.javacpp.opencv_face.FisherFaceRecognizer;
import org.bytedeco.javacpp.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.leandroinacio.picmeapi.base.BaseImageService;
import com.leandroinacio.picmeapi.user.IUserRepository;
import com.leandroinacio.picmeapi.user.User;
import com.leandroinacio.picmeapi.utils.DateUtils;
import com.leandroinacio.picmeapi.utils.FileUtils;

@Service
public class FaceService extends BaseImageService implements IFaceService {

	public static final String UPLOAD_ROOT = "/home/leandroinacio/workspace/picme-pics/face/";

	private static final Logger log = LoggerFactory.getLogger(FaceService.class);
	
	@Autowired
	private IFaceRepository faceRepository;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	public Face upload(MultipartFile file) throws IOException {
		
		// TODO: Get the user sending the file
		User user = userRepository.findById((long)1);
		
		// TODO: Double check face values
		// Create face object and save to the db
		Face face = new Face(file.getContentType(), user);
		//Face aa = new Face() {{ setFileType("aa"); }};
		face = faceRepository.save(face);
				
		// Copy to folder and rename it
		this.checkAndCreatePaths(this.getFilePath(face));
		this.copyToFolderAndRenameFile(file, this.getFilePath(face), this.getFileName(face));
		
		return face;
	}
	
	public void deleteImage(Long id) throws IOException {
		Face face = faceRepository.findById(id);
		faceRepository.deleteById(face.getId());
		Files.deleteIfExists(Paths.get(this.getFilePath(face), this.getFileName(face)));
	}
	
	public ResponseEntity<Resource> serveOneImage(Long id) {
		Face face = faceRepository.findById(id); //TODO: if null return 
		Resource file = resourceLoader.getResource("file:" + this.getFilePath(face) + "/" + this.getFileName(face));
		
		return ResponseEntity.ok()
		.contentType(MediaType.parseMediaType(face.getFileType()))
		.header(HttpHeaders.CONTENT_DISPOSITION, ":attachment; filename=\"" + file.getFilename() + "\"")
		.body(file);
	}
	
	/**
	 * @param face object
	 * @return the file name (id + file type)
	 */
	protected String getFileName(Face face) {
		return face.getId() + "." + (face.getFileType().split("/")[face.getFileType().split("/").length - 1]);
	}

	/**
	 * TODO: Check if folder should be based on create date for picture classes
	 * @param face object
	 * @return the file path (root + create date of the image)
	 */
	public String getFilePath(Face face) {
		return UPLOAD_ROOT + face.getUser().getId() + "/" + DateUtils.getDayMonthYear(face.getCreateDate()) + "/"; 
	}

	public void train(Long userId, String date) {
		
		// Get folder requested or all of them if passed null
		List<String> dateList = Arrays.asList(date);
//		if (date == null) {
//			dateList = faceRepository.findCreateDateById(userId);
//		}
		
		// Analyze each folder
		for (String currentDate : dateList) {
			
			// Get folder and files
			File folder = new File(UPLOAD_ROOT + userId + "/" + currentDate + "/");
			FilenameFilter nameFilter = FileUtils.getDefaultFilenameFilter();
			File[] files = folder.listFiles(nameFilter);
			
			// Setup info on analyzer
			MatVector faces = new MatVector(files.length);
			Mat labels = new Mat(files.length, 1, opencv_core.CV_32SC1);
			IntBuffer labelBuffer = labels.createBuffer();
			
			// Iterate images and prepare for analyze
			for (Integer pos = 0; pos < files.length; pos++) {
				File file = files[pos];
				
				// Get just the face
				Mat faceToAnalyze = FileUtils.detectFace(file);
				
				faces.put(pos, faceToAnalyze);
				labelBuffer.put(pos);
			}

			// Train facial recognition and save files
			String folderName = folder.getAbsolutePath() + "/" + userId;
	        FaceRecognizer eigenfaces = EigenFaceRecognizer.create();
	        FaceRecognizer fisherfaces = FisherFaceRecognizer.create();
	        FaceRecognizer lbph = LBPHFaceRecognizer.create();
	        eigenfaces.train(faces, labels);
	        eigenfaces.save(folderName + "_eigen.yml");
	        fisherfaces.train(faces, labels);
	        fisherfaces.save(folderName + "_fisher.yml");
	        lbph.train(faces, labels);
	        lbph.save(folderName + "_lbph.yml");
		}
	}
	
}
