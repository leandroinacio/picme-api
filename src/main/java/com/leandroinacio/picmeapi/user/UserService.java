package com.leandroinacio.picmeapi.user;

import java.time.LocalDateTime;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private IUserRepository userRepository;
	
	public void save(User user) {
		
		// Update create date and modified date
		if (user.getId() == null || user.getId() == 0) {
			user.setCreateDate(Calendar.getInstance());
		}
		user.setModifiedDate(Calendar.getInstance());
		userRepository.save(user);
	}
	
	public User findById(Integer id) {
		return userRepository.findById(id);
	}
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public User findByName(String name) {
		return userRepository.findByName(name);
	}
	
}
