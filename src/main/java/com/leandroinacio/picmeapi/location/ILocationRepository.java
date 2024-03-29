package com.leandroinacio.picmeapi.location;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.leandroinacio.picmeapi.user.User;

public interface ILocationRepository extends Repository<Location, Long>{

	public void save(Location location);
	public Location findById(Long id);
	public void deleteById(Long id);
	public Page<Location> findAll(Pageable pageable);
	public Page<Location> findByUser(User user, Pageable pageable);
	
}
