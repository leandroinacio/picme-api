package com.leandroinacio.picmeapi.permission;

import org.springframework.data.domain.Page;

public interface IPermissionService {

	public Permission save(Permission role);
	public Permission findById(Long id);
	public Page<Permission> findAll(Integer page, Integer size);
	public void deleteById(Long id);
	
}
