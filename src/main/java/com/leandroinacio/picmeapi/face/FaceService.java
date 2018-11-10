package com.leandroinacio.picmeapi.face;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.leandroinacio.picmeapi.utils.DateUtils;

@Service
public class FaceService implements IFaceService {

	private static final Logger log = LoggerFactory.getLogger(FaceService.class);
	
	private static final String UPLOAD_ROOT = "face-dir/face";
	
	@Autowired
	private IFaceRepository faceRepository;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	public Face upload(Face face, MultipartFile file) throws IOException {
		if (file.isEmpty()) { return null; }
		face = faceRepository.save(face);
		Files.copy(file.getInputStream(), Paths.get(this.getFilePath(face), this.getFileName(face)));
		return face;
	}
	
	public void deleteImage(Face face) throws IOException {
		faceRepository.deleteById(face.getId());
		Files.deleteIfExists(Paths.get(this.getFilePath(face), this.getFileName(face)));
	}
	
	public Resource serveOneImage(Face face) {
		return resourceLoader.getResource("file:" + this.getFilePath(face) + "/" + this.getFileName(face));
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
		return face.getId() + "." + face.getFileType();
	}
}
