package com.leandroinacio.picmeapi.face;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IFaceService {
	public Face upload(MultipartFile file) throws IOException;
	public void deleteImage(Long id) throws IOException;
	public ResponseEntity<Resource> serveOneImage(Long id);
}
