package com.leandroinacio.picmeapi.permission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.leandroinacio.picmeapi.role.Role;

@Service
public class PermissionService implements IPermissionService {

	private static final Logger log = LoggerFactory.getLogger(PermissionService.class);
	
	@Autowired
	public IPermissionRepository permissionRepository;
	
	public Permission save(Permission permission) {
		return this.permissionRepository.save(permission);
	}

	public Permission findById(Long id) {
		return this.permissionRepository.findById(id);
	}

	public Page<Permission> findAll(Integer page, Integer size) {
		return this.permissionRepository.findAll(PageRequest.of(page, size));
	}

	public void deleteById(Long id) {
		this.permissionRepository.deleteById(id);
	}

}
