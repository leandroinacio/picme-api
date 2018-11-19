package com.leandroinacio.picmeapi.location;

import java.util.List;

import com.leandroinacio.picmeapi.user.User;

public interface ILocationService {
	public void save(List<Location> locations);
	public Location findById(Long id);
	public void deleteById(Long id);
}
