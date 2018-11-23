package com.leandroinacio.picmeapi.picture;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.leandroinacio.picmeapi.location.Location;
import com.leandroinacio.picmeapi.user.User;

public interface IPictureRepository extends Repository<Picture, Long> {

	public Picture save(Picture picture);
	public Picture findById(Long id);
	public void deleteById(Long id);
	public Page<Picture> findByPhotographer(User photographer, Pageable pageable);
	public Page<Picture> findByOwner(User owner, Pageable pageable);
	
//	@Query("SELECT p FROM Picture p INNER JOIN Location l on (l.id = p.location) 
//		WHERE 
//			l.country=:#{#location.country}")
	public List<Picture> findByLocation(Location location);
}
