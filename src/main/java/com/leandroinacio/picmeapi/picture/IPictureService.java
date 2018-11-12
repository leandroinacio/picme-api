package com.leandroinacio.picmeapi.picture;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IPictureService {
	public Picture upload(Picture picture, MultipartFile file) throws IOException;
	public void deleteImage(Long id) throws IOException;
	public ResponseEntity<Resource> serveOneImage(Long id);
	public ResponseEntity<Resource> serveOneDemoImage(Long id);
}
