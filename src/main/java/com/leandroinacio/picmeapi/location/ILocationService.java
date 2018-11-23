package com.leandroinacio.picmeapi.location;

import java.util.List;

import org.springframework.data.domain.Page;

import com.leandroinacio.picmeapi.user.User;

public interface ILocationService {
	public void save(List<Location> locations);
	public Location findById(Long id);
	public void deleteById(Long id);
	public Page<Location> findAll(Integer page, Integer size);
	public Page<Location> findByUser(User user, Integer page, Integer size);
}
