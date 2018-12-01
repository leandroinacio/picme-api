package com.leandroinacio.picmeapi.face;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.leandroinacio.picmeapi.jwt.JwtUser;

public interface IFaceService {
	
	public Face upload(MultipartFile file, JwtUser jwtUser) throws IOException;
	public Page<Face> findAll(Integer page, Integer size);
	public Page<Face> findByUser(JwtUser jwtUser, Integer page, Integer size);
	public Face serveOneImageById(Long id, JwtUser jwtUser);
	public void deleteImage(Long id, JwtUser jwtUser) throws IOException;
	public void train(JwtUser jwtUser);
	
}
