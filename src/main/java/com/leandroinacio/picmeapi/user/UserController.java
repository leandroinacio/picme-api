package com.leandroinacio.picmeapi.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private IUserService userService;
	
	@PutMapping("/save")
	public HttpStatus save(@RequestBody User user) {
		try {
			userService.save(user);
			return HttpStatus.OK;
		} catch(Exception e) {
			log.error(e.getMessage());
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}
	
	@GetMapping("/findById/{id}")
	public @ResponseBody User findById(@PathVariable("id") Long id) {
		try {
			return userService.findById(id);
		}catch(Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	@GetMapping("/findByEmail/{email}")
	public @ResponseBody User findByEmail(@PathVariable("email") String email) {
		try {
			return userService.findByEmail(email);
		}catch(Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	@GetMapping("/findByName/{name}")
	public @ResponseBody User findByName(@PathVariable("name") String name) {
		try {
			return userService.findByName(name);
		}catch(Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public HttpStatus delete(@PathVariable("id") Long id) {
		try {
			userService.deleteById(id);
			return HttpStatus.OK;
		}catch(Exception e) {
			log.error(e.getMessage());
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}
}
