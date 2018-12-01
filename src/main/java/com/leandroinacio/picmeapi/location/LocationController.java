package com.leandroinacio.picmeapi.location;

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

import com.leandroinacio.picmeapi.base.BaseResponse;
import com.leandroinacio.picmeapi.jwt.JwtUser;

@RestController @RequestMapping("/location")
public class LocationController {

	private static final Logger log = LoggerFactory.getLogger(LocationController.class);
	
	@Autowired
	private ILocationService locationService;
	
	@PreAuthorize("hasAuthority('UPSERT_LOCATION')")
	@PutMapping("/save")
	public BaseResponse save(@RequestBody Location location, Authentication auth) {
		this.locationService.save(location, (JwtUser) auth.getPrincipal());
		return new BaseResponse();
	}
	
	@PreAuthorize("hasAuthority('FIND_LOCATION')")
	@GetMapping("/findById/{id}")
	public BaseResponse findById(@PathVariable Long id, Authentication auth) {
		return new BaseResponse(this.locationService.findById(id, (JwtUser) auth.getPrincipal()));
	}
	
	@PreAuthorize("hasAuthority('FIND_ALL_LOCATION')")
	@GetMapping("/findAll/{page}/{size}")
	public BaseResponse findById(@PathVariable Integer page, @PathVariable Integer size) {
		return new BaseResponse(this.locationService.findAll(page, size));
	}

	@PreAuthorize("hasAuthority('FIND_USER_LOCATION')")
	@GetMapping("/findByUser/{page}/{size}")
	public BaseResponse findByUser(@PathVariable Integer page, @PathVariable Integer size, Authentication auth) {		
		return new BaseResponse(this.locationService.findByUser((JwtUser) auth.getPrincipal(), page, size));
	}
	
	@PreAuthorize("hasAuthority('DELETE_USER_LOCATION')")
	@DeleteMapping("/delete/{id}")
	public BaseResponse delete(@PathVariable Long id, Authentication auth) {
		this.locationService.deleteById(id, (JwtUser) auth.getPrincipal());
		return new BaseResponse();
	}
	
}
