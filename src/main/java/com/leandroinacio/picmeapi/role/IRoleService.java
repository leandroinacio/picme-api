package com.leandroinacio.picmeapi.role;

import org.springframework.data.domain.Page;

public interface IRoleService {

	public Role save(Role role);
	public Role findById(Long id);
	public Page<Role> findAll(Integer page, Integer size);
	public void deleteById(Long id);
	
}
