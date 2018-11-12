package com.leandroinacio.picmeapi.picture;

import org.springframework.data.repository.Repository;

public interface IPictureRepository extends Repository<Picture, Long> {

	public Picture save(Picture picture);
	public Picture findById(Long id);
	public void deleteById(Long id);
	
}
