package com.leandroinacio.picmeapi.permission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PermissionService implements IPermissionService {

	private static final Logger log = LoggerFactory.getLogger(PermissionService.class);
	
	@Autowired
	public IPermissionRepository roleRepository;
	
	public Permission save(Permission role) {
		return this.roleRepository.save(role);
	}

	public Permission findById(Long id) {
		return this.roleRepository.findById(id);
	}

	public Page<Permission> findAll(Integer page, Integer size) {
		return this.roleRepository.findAll(PageRequest.of(page, size));
	}

	public void deleteById(Long id) {
		this.roleRepository.deleteById(id);
	}

}
