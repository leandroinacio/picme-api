package com.leandroinacio.picmeapi.user;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void save(User user) {
		if (user.getLastPasswordReset() == null) {
			user.setLastPasswordReset(Calendar.getInstance());
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	public Page<User> findAll(Integer page, Integer size) {
		return userRepository.findAll(PageRequest.of(page, size));
	}
	
	public User findById(Long id) {
		return userRepository.findById(id);
	}
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public User findByFirstName(String firstName) {
		return userRepository.findByFirstName(firstName);
	}
	
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}	

}
