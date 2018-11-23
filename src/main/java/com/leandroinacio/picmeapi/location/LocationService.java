package com.leandroinacio.picmeapi.location;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.leandroinacio.picmeapi.user.User;

@Service
public class LocationService implements ILocationService {

	private static final Logger log = LoggerFactory.getLogger(LocationService.class);
	
	@Autowired
	private ILocationRepository locationRepository;

	public void save(List<Location> locations) {
		if (locations != null && locations.size() > 0) {
			this.locationRepository.save(locations);
		}
	}

	public Location findById(Long id) {
		if (id == null) { return null; }
		return this.locationRepository.findById(id);
	}

	public void deleteById(Long id) {
		if (id != null) {
			this.locationRepository.deleteById(id);
		}
	}

	public Page<Location> findAll(Integer page, Integer size) {
		return this.locationRepository.findAll(PageRequest.of(page, size));
	}

	public Page<Location> findByUser(User user, Integer page, Integer size) {
		return this.locationRepository.findByUser(user, PageRequest.of(page, size));
	}
	
}
