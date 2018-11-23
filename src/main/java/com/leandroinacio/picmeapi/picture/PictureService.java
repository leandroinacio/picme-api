package com.leandroinacio.picmeapi.picture;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;

import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_face.EigenFaceRecognizer;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.leandroinacio.picmeapi.location.Location;
import com.leandroinacio.picmeapi.user.IUserRepository;
import com.leandroinacio.picmeapi.user.User;
import com.leandroinacio.picmeapi.utils.FileUtils;

@Service
public class PictureService implements IPictureService {

	@Autowired
	private IPictureRepository pictureRepository;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	public Picture upload(Picture picture, MultipartFile file) throws IOException {
		
		// TODO: Get the photographer sending the picture
		User photographer = userRepository.findById((long) 1);
		
		// TODO: Double check picture values (photographer)
		// Setup missing values from picture and save to the db
		picture.setPhotographer(photographer);
		if (picture.getPictureDate() == null) {
			picture.setPictureDate(Calendar.getInstance());
		}
		picture.setFileType(file.getContentType());		
		picture = this.pictureRepository.save(picture);
		
		// Copy to folder and rename it
		FileUtils.copyToFolderAndRenameFile(file, FileUtils.getPhotographerPicturePath(picture), 
				FileUtils.getFileName(picture.getId(), picture.getFileType()));
		
		// TODO: Create demo picture and folder.
		
		return picture;
	}
	
	public void addOwner(Picture picture, User user) throws IOException {
		
		// Add new user as owner of the picture
		picture.getOwner().add(user);
		this.pictureRepository.save(picture);
		
		// Move file to new owner folder
		Files.copy(
				Paths.get(FileUtils.getPhotographerPicturePath(picture), 
						FileUtils.getFileName(picture.getId(), picture.getFileType())), 
				Paths.get(FileUtils.getOwnerPicturePath(picture), 
						FileUtils.getFileName(picture.getId(), picture.getFileType()))
			);
	}
	
	public void deleteImage(Picture picture, User user) throws IOException {
		
		// TODO: Figure out how to do security on who can delete it
		// User user = this.pictureRepository.findByIdAndUser(picture.getId(), user);
		this.pictureRepository.deleteById(picture.getId());
		
		// TODO: Figure out best way to handle if picture is from owner or from photographer
		Files.deleteIfExists(Paths.get(FileUtils.getOwnerPicturePath(picture), 
				FileUtils.getFileName(picture.getId(), picture.getFileType())));
		// Files.deleteIfExists(Paths.get(this.getDemoFilePath(picture), this.getFileName(picture)));
	}
	
	public Picture serveOneImageById(Long id) {
		Picture picture = this.pictureRepository.findById(id);
		
		// TODO: Figure out security to handle photographer vs owner folder
		String filePath = FileUtils.getPhotographerPicturePath(picture) 
				+ FileUtils.getFileName(picture.getId(), picture.getFileType());
		picture.setFile(resourceLoader.getResource("file:" + filePath));
		return picture;
	}
	
	public Page<Picture> findByPhotographer(User photographer, Integer page, Integer size) {
		return this.pictureRepository.findByPhotographer(photographer, PageRequest.of(page, size));
	}

	public Page<Picture> findByOwner(User owner, Integer page, Integer size) {
		return this.pictureRepository.findByOwner(owner, PageRequest.of(page, size));
	}
			
	public List<Picture> searchPicturesAndAnalyze(User user, List<Location> locations) {
		
		// Find pictures that match locations
		// TODO: Update findByLocation to retrieve all locations
		List<Picture> pictures = this.pictureRepository.findByLocation(locations.get(0));
		
		// Setup file
		// TODO: Need to handle all files retrieved
		File file = new File(FileUtils.getOwnerPicturePath(pictures.get(0)) 
				+ FileUtils.getFileName(pictures.get(0).getId(), pictures.get(0).getFileType()));
		
		// Get faces on picture
		List<Mat> facesToIdentify = FileUtils.detectFace(file);
		
		// Check for faces
		IntPointer label = new IntPointer(1);
		DoublePointer confidence = new DoublePointer(2);
		FaceRecognizer eigenRecognizer = EigenFaceRecognizer.create();
		
		// Get faces recognition for user				
		//TODO: Work on logic here, add other face recognition methods
		eigenRecognizer.read(FileUtils.getFaceYmlByUser(user, "eigen"));
		
		for (Mat currentFace : facesToIdentify) {
			eigenRecognizer.predict(currentFace, label, confidence);			
		}
		
		return null;
	}
	
}
