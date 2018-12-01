package com.leandroinacio.picmeapi.permission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leandroinacio.picmeapi.base.BaseController;
import com.leandroinacio.picmeapi.base.BaseResponse;
import com.leandroinacio.picmeapi.role.Role;

// TODO: Security, many-to-many problem with the role table
@RestController @RequestMapping("/permission")
public class PermissionController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(PermissionController.class);
	
	@Autowired
	private IPermissionService permissionService;
	
	@PutMapping("/save")
	public BaseResponse save(@RequestBody Permission permission) {
		return new BaseResponse(this.permissionService.save(permission));
	}

	@GetMapping("/findAll/{page}/{size}")
	public BaseResponse findAll(@PathVariable Integer page, @PathVariable Integer size) {
		return new BaseResponse(this.permissionService.findAll(page, size));
	}

	@PostMapping("/findByRole")
	public BaseResponse findByRole(@RequestBody Role role, @RequestBody Integer page, @RequestBody Integer size) {
		return new BaseResponse(this.permissionService.findByRole(role, page, size));
	}
	
	@GetMapping("/findById/{id}")
	public BaseResponse findById(@PathVariable Long id) {
		return new BaseResponse(this.permissionService.findById(id));
	}
	
	@DeleteMapping("/delete/{id}")
	public BaseResponse delete(@PathVariable Long id) {
		this.permissionService.deleteById(id);
		return new BaseResponse();
	}
	
}
