package com.leandroinacio.picmeapi.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public @ResponseBody HttpStatus save(@RequestBody User user) {
		try {
			userService.save(user);
			return HttpStatus.OK;
		} catch(Exception e) {
			log.error(e.getMessage());
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}
	
	@GetMapping("/welcome")
	public String welcome() {
		return "welcome";
	}
	
	@GetMapping("/findById/{id}")
	public @ResponseBody User findById(@PathVariable("id") Integer id) {
		try {
			User user = userService.findById(id);
			return user;
		}catch(Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	@GetMapping("/findByEmail/{email}")
	public ResponseEntity<User> findByEmail(@PathVariable("email") String email) {
		try {
			User user = userService.findByEmail(email);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}catch(Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/findByName/{name}")
	public ResponseEntity<User> findByName(@PathVariable("name") String name) {
		try {
			User user = userService.findByName(name);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}catch(Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
