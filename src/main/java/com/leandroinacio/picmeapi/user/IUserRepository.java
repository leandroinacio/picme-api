package com.leandroinacio.picmeapi.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface IUserRepository extends Repository<User, Long>{

	public void save(User user);

	public Page<User> findAll(Pageable pageable);
	
	@Query("SELECT user FROM User user WHERE user.id =:id")
	@Transactional(readOnly=true)
	public User findById(@Param("id") Long id);
	
	@Transactional(readOnly=true)
	public User findByFirstName(String firstName);
	
	@Query("SELECT user FROM User user WHERE user.email =:email")
	@Transactional(readOnly=true)
	public User findByEmail(@Param("email") String email);
	
	public void deleteById(Long id);
	
}
