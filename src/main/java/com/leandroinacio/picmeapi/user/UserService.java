package com.leandroinacio.picmeapi.user;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.leandroinacio.picmeapi.jwt.JwtUser;

@Service
public class UserService implements IUserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void save(User user, Authentication auth) {
		
		// Get the user if it's logged in
		JwtUser jwtUser = null;
		if (auth != null) {
			jwtUser = (JwtUser) auth.getPrincipal();
		}
		
		// Return if logged used is trying to update a different user
		if (jwtUser != null && user.getId() != jwtUser.getId()) {
			return;
		}
		
		// If it's new user, encode password and set last password reset
		// Or if it's logged user changing password, do the same
		if (jwtUser == null || (jwtUser != null && !user.getPassword().isEmpty())) {
			user.setLastPasswordReset(Calendar.getInstance());
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		
		userRepository.save(user);
	}
	
	public Page<User> findAll(Integer page, Integer size) {
		
		// TODO: Change this so the query won't bring any password
		Page<User> users = userRepository.findAll(PageRequest.of(page, size));
		
		users.getContent().forEach(user -> {
			user.setPassword(null);
		});
		
		return users;
	}
	
	public User findById(Long id) {
		User user = userRepository.findById(id);
		user.setPassword(null);
		return user;
	}
	
	public User findByEmail(String email) {
		User user = userRepository.findByEmail(email);
		user.setPassword(null);
		return user;
	}
	
	public User findByFirstName(String firstName) {
		User user = userRepository.findByFirstName(firstName);
		user.setPassword(null);
		return user;
	}
	
	public void deleteById(Long id, JwtUser jwtUser) {
		if (id == jwtUser.getId()) {
			userRepository.deleteById(id);
		}
	}	

}
