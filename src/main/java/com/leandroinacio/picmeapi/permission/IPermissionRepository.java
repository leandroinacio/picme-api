package com.leandroinacio.picmeapi.permission;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface IPermissionRepository extends Repository<Permission, Long> {

	public Permission save(Permission role);
	public Permission findById(Long id);
	public Page<Permission> findAll(Pageable pageable);
	public void deleteById(Long id);

}
