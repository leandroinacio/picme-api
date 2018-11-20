package com.leandroinacio.picmeapi.picture;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.leandroinacio.picmeapi.location.Location;
import com.leandroinacio.picmeapi.user.User;

public interface IPictureRepository extends Repository<Picture, Long> {

	public Picture save(Picture picture);
	public Picture findById(Long id);
	public void deleteById(Long id);
	public List<Picture> findByPhotographer(User photographer);
	
//	@Query("SELECT p FROM Picture p INNER JOIN Location l on (l.id = p.location) 
//		WHERE 
//			l.country=:#{#location.country}")
	public List<Picture> findByLocation(Location location);
}
