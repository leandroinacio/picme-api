package com.leandroinacio.picmeapi.picture;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;

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

@Service
public class PictureService extends BaseImageService implements IPictureService {

	public static final String UPLOAD_ROOT = "/home/leandroinacio/workspace/picme-pics/picture/";
	
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
		picture = pictureRepository.save(picture);
		
		// TODO: Check about folder creation. Should we do it separated? One for each class?
		// Copy to folder and rename it
		this.checkAndCreatePaths(this.getPhotographerFilePath(picture));
		this.copyToFolderAndRenameFile(file, this.getPhotographerFilePath(picture), this.getFileName(picture));
		
		// TODO: Create demo picture and folder.
		
		return picture;
	}
	
	public void addOwner(Picture picture, User user) throws IOException {
		picture.getOwner().add(user);
		pictureRepository.save(picture);
		Files.copy(
				Paths.get(this.getPhotographerFilePath(picture), this.getFileName(picture)), 
				Paths.get(this.getUserFilePath(picture, user), this.getFileName(picture))
			);
	}
	
	public void deleteImage(Long id) throws IOException {
		Picture picture = pictureRepository.findById(id);
		pictureRepository.deleteById(id);
		Files.deleteIfExists(Paths.get(this.getPhotographerFilePath(picture), this.getFileName(picture)));
		Files.deleteIfExists(Paths.get(this.getDemoFilePath(picture), this.getFileName(picture)));
	}
	
	public ResponseEntity<Resource> serveOneImage(Long id) {
		Picture picture = pictureRepository.findById(id);
		Resource file = resourceLoader.getResource("file:" + this.getPhotographerFilePath(picture) + "/" + this.getFileName(picture));
		
		return ResponseEntity.ok()
		.contentType(MediaType.parseMediaType(picture.getFileType()))
		.header(HttpHeaders.CONTENT_DISPOSITION, ":attachment; filename=\"" + file.getFilename() + "\"")
		.body(file);
	}
	
	public ResponseEntity<Resource> serveOneDemoImage(Long id) {
		Picture picture = pictureRepository.findById(id);
		Resource file = resourceLoader.getResource("file:" + this.getDemoFilePath(picture) + "/" + this.getFileName(picture));
		
		return ResponseEntity.ok()
		.contentType(MediaType.parseMediaType(picture.getFileType()))
		.header(HttpHeaders.CONTENT_DISPOSITION, ":attachment; filename=\"" + file.getFilename() + "\"")
		.body(file);
	}
	
	public List<Picture> findByPhotographer(User photographer) {
		return pictureRepository.findByPhotographer(photographer);
	}
		
	/**
	 * @param face object
	 * @return the file path (root + original + photographerId + create date of the image)
	 */
	public String getPhotographerFilePath(Picture picture) {
		return UPLOAD_ROOT + "original/" + picture.getPhotographer().getId() + "/" + DateUtils.getDayMonthYear(picture.getCreateDate()) + "/"; 
	}

	/**
	 * @param picture
	 * @param user
	 * @return the file path (root + user + userId + create date)
	 */
	public String getUserFilePath(Picture picture, User user) {
		return UPLOAD_ROOT + "user/" + user.getId() + "/" + DateUtils.getDayMonthYear(picture.getCreateDate()) + "/";
	}
	
	/**
	 * @param face object
	 * @return the demonstration file path (root + demo + photographerId + create date of the image)
	 */
	public String getDemoFilePath(Picture picture) {
		return UPLOAD_ROOT + "demo/" + picture.getPhotographer().getId() + "/" + DateUtils.getDayMonthYear(picture.getCreateDate()) + '/'; 
	}
	
	/**
	 * @param picture object
	 * @return the file name (id + file type)
	 */
	public String getFileName(Picture picture) {
		return picture.getId() + "." + (picture.getFileType().split("/")[picture.getFileType().split("/").length - 1]);
	}
	
}
