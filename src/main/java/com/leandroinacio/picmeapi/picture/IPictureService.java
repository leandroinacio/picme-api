package com.leandroinacio.picmeapi.picture;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.leandroinacio.picmeapi.user.User;

public interface IPictureService {
	public Picture upload(Picture picture, MultipartFile file) throws IOException;
	public void addOwner(Picture picture, User user) throws IOException;
	public void deleteImage(Long id) throws IOException;
	public ResponseEntity<Resource> serveOneImage(Long id);
	public ResponseEntity<Resource> serveOneDemoImage(Long id);
	public List<Picture> findByPhotographer(User photographer);
}
