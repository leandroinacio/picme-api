package com.leandroinacio.picmeapi.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface IUserRepository extends Repository<User, Integer>{

	public void save(User user);
	
	@Query("SELECT user FROM User user WHERE user.id =:id")
	@Transactional(readOnly=true)
	public User findById(@Param("id") Integer id);
	
	@Query("SELECT user FROM User user WHERE user.name =:name")
	@Transactional(readOnly=true)
	public User findByName(@Param("name") String name);
	
	@Query("SELECT user FROM User user WHERE user.email =:email")
	@Transactional(readOnly=true)
	public User findByEmail(@Param("email") String email);
	
}
