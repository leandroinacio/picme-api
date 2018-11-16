package com.leandroinacio.picmeapi.picture;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.leandroinacio.picmeapi.user.User;

public interface IPictureRepository extends Repository<Picture, Long> {

	public Picture save(Picture picture);
	public Picture findById(Long id);
	public void deleteById(Long id);
	public List<Picture> findByPhotographer(User photographer);
	
}
