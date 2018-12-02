package com.leandroinacio.picmeapi.role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class RoleController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private IRoleService roleService;

	@PreAuthorize("hasAuthority('ROLE_CRUD')")
	@PutMapping("/save")
	public BaseResponse save(@RequestBody Role role) {
		return new BaseResponse(this.roleService.save(role));
	}

	@PreAuthorize("hasAuthority('ROLE_CRUD')")
	@GetMapping("/findAll/{page}/{size}")
	public BaseResponse findAll(@PathVariable Integer page, @PathVariable Integer size) {
		return new BaseResponse(this.roleService.findAll(page, size));
	}

	@PreAuthorize("hasAuthority('ROLE_CRUD')")
	@GetMapping("/findById/{id}")
	public BaseResponse findById(@PathVariable Long id) {
		return new BaseResponse(this.roleService.findById(id));
	}

	@PreAuthorize("hasAuthority('ROLE_CRUD')")
	@DeleteMapping("/delete/{id}")
	public BaseResponse delete(@PathVariable Long id) {
		this.roleService.deleteById(id);
		return new BaseResponse();
	}
	
}
