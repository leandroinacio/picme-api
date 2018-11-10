package com.leandroinacio.picmeapi.face;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

import com.leandroinacio.picmeapi.user.IUserRepository;
import com.leandroinacio.picmeapi.user.User;
import com.leandroinacio.picmeapi.utils.DateUtils;

@Service
public class FaceService implements IFaceService {

	private static final Logger log = LoggerFactory.getLogger(FaceService.class);
	private static final String UPLOAD_ROOT = "/home/leandroinacio/workspace/picme-pics";
	
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
		
		// Check if directory exists and create
		// if (Files.)
		
		// Copy to folder and rename it
		Files.copy(file.getInputStream(), Paths.get(this.getFilePath(face), file.getOriginalFilename()));
		File oldName = new File(this.getFilePath(face) + "/" + file.getOriginalFilename());
		File newName = new File(this.getFilePath(face) + "/" + this.getFileName(face));
		oldName.renameTo(newName);
		
		return face;
	}
	
	public void deleteImage(Long id) throws IOException {
		Face face = faceRepository.findById(id);
		faceRepository.deleteById(face.getId());
		Files.deleteIfExists(Paths.get(this.getFilePath(face), this.getFileName(face)));
	}
	
	public ResponseEntity<Resource> serveOneImage(Long id) {
		Face face = faceRepository.findById(id);
		Resource file = resourceLoader.getResource("file:" + this.getFilePath(face) + "/" + this.getFileName(face));
		
		return ResponseEntity.ok()
		.contentType(MediaType.parseMediaType(face.getFileType()))
		.header(HttpHeaders.CONTENT_DISPOSITION, ":attachment; filename=\"" + file.getFilename() + "\"")
		.body(file);
	}
		
	/**
	 * @param face object
	 * @return the file path (root + create date of the image)
	 */
	private String getFilePath(Face face) {
		return UPLOAD_ROOT + "/" + DateUtils.getDayMonthYear(face.getCreateDate()); 
	}
	
	/**
	 * @param face object
	 * @return the file name (id + file type)
	 */
	private String getFileName(Face face) {
		return face.getId() + "." + (face.getFileType().split("/")[face.getFileType().split("/").length - 1]);
	}
}
