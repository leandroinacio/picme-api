package com.leandroinacio.picmeapi.face;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.leandroinacio.picmeapi.user.User;

public interface IFaceService {
	
	public Face upload(MultipartFile file) throws IOException;
	public Page<Face> findAll(Integer page, Integer size);
	public Page<Face> findByUser(User user, Integer page, Integer size);
	public Face serveOneImageById(Long id);
//	public ResponseEntity<Resource> serveOneImage(Long id);
	public void deleteImage(Long id) throws IOException;
	public void train(Long userId);
	
}
