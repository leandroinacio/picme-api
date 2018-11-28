package com.leandroinacio.picmeapi.role;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface IRoleRepository extends Repository<Role, Long> {

	public Role save(Role role);
	public Role findById(Long id);
	public Page<Role> findAll(Pageable pageable);
	public void deleteById(Long id);

}
