package com.leandroinacio.picmeapi.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leandroinacio.picmeapi.base.BaseController;
import com.leandroinacio.picmeapi.base.BaseResponse;
import com.leandroinacio.picmeapi.jwt.JwtUser;

@RestController @RequestMapping("/user")
public class UserController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
		
	@Autowired
	private IUserService userService;

	@PutMapping("/save")
	public BaseResponse save(@RequestBody User user, Authentication auth) {
		this.userService.save(user, auth);
		return new BaseResponse();
	}

	@PreAuthorize("hasAuthority('USER_FIND_ALL')")
	@GetMapping("/findAll/{page}/{size}")
	public BaseResponse findAll(@PathVariable Integer page, @PathVariable Integer size) {
		return new BaseResponse(this.userService.findAll(page, size));
	}
	
	@PreAuthorize("hasAuthority('USER_FIND')")
	@GetMapping("/findById/{id}")
	public BaseResponse findById(@PathVariable Long id) {
		return new BaseResponse(this.userService.findById(id));
	}
	
	@PreAuthorize("hasAuthority('USER_FIND')")
	@GetMapping("/findByEmail/{email}")
	public BaseResponse findByEmail(@PathVariable String email) {
		return new BaseResponse(this.userService.findByEmail(email));
	}
	
	@PreAuthorize("hasAuthority('USER_FIND')")
	@GetMapping("/findByFirstName/{firstName}")
	public BaseResponse findByName(@PathVariable String firstName) {
		return new BaseResponse(this.userService.findByFirstName(firstName));
	}
	
	@PreAuthorize("hasAuthority('USER_DELETE')")
	@DeleteMapping("/delete/{id}")
	public BaseResponse delete(@PathVariable Long id, Authentication auth) {
		this.userService.deleteById(id, (JwtUser) auth.getPrincipal());
		return new BaseResponse();
	}
}
