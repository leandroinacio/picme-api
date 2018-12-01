package com.leandroinacio.picmeapi.picture;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.leandroinacio.picmeapi.jwt.JwtUser;
import com.leandroinacio.picmeapi.location.Location;
import com.leandroinacio.picmeapi.user.User;

public interface IPictureService {
	public Picture upload(Picture picture, MultipartFile file, JwtUser jwtUser) throws IOException;
	public void addOwner(Picture picture, JwtUser jwtUser) throws IOException;
	public void deleteImage(Picture picture, JwtUser jwtUser) throws IOException;
	public Picture serveOneImageById(Long id, JwtUser jwtUser);
	public Page<Picture> findByPhotographer(User photographer, Integer page, Integer size);
	public Page<Picture> findByOwner(JwtUser jwtUser, Integer page, Integer size);
	public List<Picture> searchPicturesAndAnalyze(JwtUser jwtUser, List<Location> locations);
}
