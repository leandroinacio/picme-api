package com.leandroinacio.picmeapi.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leandroinacio.picmeapi.base.BaseController;
import com.leandroinacio.picmeapi.base.BaseResponse;

@RestController @RequestMapping("/user")
public class UserController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
		
	@Autowired
	private IUserService userService;

	@PutMapping("/save")
	public BaseResponse save(@RequestBody User user) {
		this.userService.save(user);
		return new BaseResponse();
	}

	@GetMapping("/findAll/{page}/{size}")
	public BaseResponse findAll(@PathVariable Integer page, @PathVariable Integer size) {
		return new BaseResponse(this.userService.findAll(page, size));
	}
	
	@GetMapping("/findById/{id}")
	public BaseResponse findById(@PathVariable Long id) {
		return new BaseResponse(this.userService.findById(id));
	}
	
	@GetMapping("/findByEmail/{email}")
	public BaseResponse findByEmail(@PathVariable String email) {
		return new BaseResponse(this.userService.findByEmail(email));
	}
	
	@GetMapping("/findByFirstName/{firstName}")
	public BaseResponse findByName(@PathVariable String firstName) {
		return new BaseResponse(this.userService.findByFirstName(firstName));
	}
	
	@DeleteMapping("/delete/{id}")
	public BaseResponse delete(@PathVariable Long id) {
		this.userService.deleteById(id);
		return new BaseResponse();
	}
}
