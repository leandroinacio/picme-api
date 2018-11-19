package com.leandroinacio.picmeapi.location;

import java.util.List;

import org.springframework.data.repository.Repository;

public interface ILocationRepository extends Repository<Location, Long>{

	public void save(List<Location> locations);
	public Location findById(Long id);
	public void deleteById(Long id);
	public List<Location> findAll();
	
}
