package com.leandroinacio.picmeapi.location;

import java.util.List;

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

import com.leandroinacio.picmeapi.base.BaseResponse;
import com.leandroinacio.picmeapi.user.User;

@RestController
@RequestMapping("/location")
public class LocationController {

	private static final Logger log = LoggerFactory.getLogger(LocationController.class);
	
	@Autowired
	private ILocationService locationService;
	
	@PutMapping("/save")
	public BaseResponse save(@RequestBody List<Location> locations) {
		this.locationService.save(locations);
		return new BaseResponse();
	}
	
	@GetMapping("/findById/{id}")
	public BaseResponse findById(@PathVariable("id") Long id) {
		return new BaseResponse(this.locationService.findById(id));
	}
	
	@GetMapping("/findAll/{page}/{size}")
	public BaseResponse findById(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
		return new BaseResponse(this.locationService.findAll(page, size));
	}
	
	@GetMapping("/findByUser/{id}/{page}/{size}")
	public BaseResponse findByUser(@PathVariable("id") Long id, 
			@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
		
		// TODO: Apply security rules here
		User user = new User() {{ setId(id); }};
		return new BaseResponse(this.locationService.findByUser(user, page, size));
	}
	
	@DeleteMapping("/delete/{id}")
	public BaseResponse delete(@PathVariable("id") Long id) {
		this.locationService.deleteById(id);
		return new BaseResponse();
	}
	
}
