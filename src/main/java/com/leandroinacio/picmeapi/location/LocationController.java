package com.leandroinacio.picmeapi.location;

import java.util.List;

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
@RequestMapping("/location")
public class LocationController {

	private static final Logger log = LoggerFactory.getLogger(LocationController.class);
	
	@Autowired
	private ILocationService locationService;
	
	@PutMapping("/save")
	public HttpStatus save(@RequestBody List<Location> locations) {
		try {
			locationService.save(locations);
			return HttpStatus.OK;
		} catch(Exception e) {
			log.error(e.getMessage());
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}
	
	@GetMapping("/findById/{id}")
	public @ResponseBody Location findById(@PathVariable("id") Long id) {
		try {
			return locationService.findById(id);
		}catch(Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public HttpStatus delete(@PathVariable("id") Long id) {
		try {
			locationService.deleteById(id);
			return HttpStatus.OK;
		}catch(Exception e) {
			log.error(e.getMessage());
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}
	
}
