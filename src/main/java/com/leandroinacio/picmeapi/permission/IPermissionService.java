package com.leandroinacio.picmeapi.permission;

import org.springframework.data.domain.Page;

import com.leandroinacio.picmeapi.role.Role;

public interface IPermissionService {

	public Permission save(Permission permission);
	public Permission findById(Long id);
	public Page<Permission> findAll(Integer page, Integer size);
	public Page<Permission> findByRole(Role role, Integer page, Integer size);
	public void deleteById(Long id);
	
}
