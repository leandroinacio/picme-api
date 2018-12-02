package com.leandroinacio.picmeapi.user;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import com.leandroinacio.picmeapi.jwt.JwtUser;

public interface IUserService {

	public void save(User user, Authentication auth);
	public Page<User> findAll(Integer page, Integer size);
	public User findByFirstName(String firstName);
	public User findByEmail(String email);
	public User findById(Long id);
	public void deleteById(Long id, JwtUser jwtUser);
	
}
