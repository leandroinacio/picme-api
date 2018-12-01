package com.leandroinacio.picmeapi.location;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.leandroinacio.picmeapi.jwt.JwtUser;
import com.leandroinacio.picmeapi.user.User;

@Service
public class LocationService implements ILocationService {

	private static final Logger log = LoggerFactory.getLogger(LocationService.class);
	
	@Autowired
	private ILocationRepository locationRepository;

	public void save(Location location, JwtUser jwtUser) {
		User user = new User(jwtUser);
		if (location != null) {
			location.setUser(user);
			this.locationRepository.save(location);
		}
	}

	public Location findById(Long id, JwtUser jwtUser) {
		Location location = this.locationRepository.findById(id);
		if (location.getUser().getId() != jwtUser.getId()) {
			return location;
		}
		return null;
	}

	public void deleteById(Long id, JwtUser jwtUser) {
		Location location = this.locationRepository.findById(id);
		if (location.getUser().getId() == jwtUser.getId()) {
			this.locationRepository.deleteById(id);
		}
	}

	public Page<Location> findAll(Integer page, Integer size) {
		return this.locationRepository.findAll(PageRequest.of(page, size));
	}

	public Page<Location> findByUser(JwtUser jwtUser, Integer page, Integer size) {
		User user = new User(jwtUser);
		return this.locationRepository.findByUser(user, PageRequest.of(page, size));
	}
	
}
