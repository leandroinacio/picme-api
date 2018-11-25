package com.leandroinacio.picmeapi.role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {

	private static final Logger log = LoggerFactory.getLogger(RoleService.class);
	
	@Autowired
	public IRoleRepository roleRepository;
	
	public Role save(Role role) {
		return this.roleRepository.save(role);
	}

	public Role findById(Long id) {
		return this.roleRepository.findById(id);
	}

	public Page<Role> findAll(Integer page, Integer size) {
		return this.roleRepository.findAll(PageRequest.of(page, size));
	}

	public void deleteById(Long id) {
		this.roleRepository.deleteById(id);
	}

}
