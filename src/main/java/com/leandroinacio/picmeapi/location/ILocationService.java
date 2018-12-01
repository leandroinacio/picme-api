package com.leandroinacio.picmeapi.location;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import com.leandroinacio.picmeapi.jwt.JwtUser;

public interface ILocationService {
	public void save(Location location, JwtUser jwtUser);
	public Location findById(Long id, JwtUser jwtUser);
	public void deleteById(Long id, JwtUser jwtUser);
	public Page<Location> findAll(Integer page, Integer size);
	public Page<Location> findByUser(JwtUser jwtUser, Integer page, Integer size);
}
