package com.leandroinacio.picmeapi.permission;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.leandroinacio.picmeapi.role.Role;

public interface IPermissionRepository extends Repository<Permission, Long> {

	public Permission save(Permission permission);
	public Permission findById(Long id);
	public Page<Permission> findAll(Pageable pageable);
	public Page<Permission> findByRoles(Role role, Pageable pageable);
	public void deleteById(Long id);

}
