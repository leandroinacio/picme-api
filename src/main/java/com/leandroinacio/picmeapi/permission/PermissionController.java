package com.leandroinacio.picmeapi.permission;

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

@RestController @RequestMapping("/role")
public class PermissionController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(PermissionController.class);
	
	@Autowired
	private IPermissionService roleService;
	
	@PutMapping("/save")
	public BaseResponse save(@RequestBody Permission role) {
		return new BaseResponse(this.roleService.save(role));
	}

	@GetMapping("/findAll/{page}/{size}")
	public BaseResponse findAll(@PathVariable Integer page, @PathVariable Integer size) {
		return new BaseResponse(this.roleService.findAll(page, size));
	}
	
	@GetMapping("/findById/{id}")
	public BaseResponse findById(@PathVariable Long id) {
		return new BaseResponse(this.roleService.findById(id));
	}
	
	@DeleteMapping("/delete/{id}")
	public BaseResponse delete(@PathVariable Long id) {
		this.roleService.deleteById(id);
		return new BaseResponse();
	}
	
}
