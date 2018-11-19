package com.leandroinacio.picmeapi.location;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leandroinacio.picmeapi.base.BaseImageService;

@Service
public class LocationService extends BaseImageService implements ILocationService {

	private static final Logger log = LoggerFactory.getLogger(LocationService.class);
	
	@Autowired
	private ILocationRepository locationRepository;

	public void save(List<Location> locations) {
		if (locations != null && locations.size() > 0) {
			locationRepository.save(locations);
		}
	}

	public Location findById(Long id) {
		if (id == null) { return null; }
		return locationRepository.findById(id);
	}

	public void deleteById(Long id) {
		if (id != null) {
			locationRepository.deleteById(id);
		}
	}
	

	
}
