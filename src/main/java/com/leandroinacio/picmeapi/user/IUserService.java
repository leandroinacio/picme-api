package com.leandroinacio.picmeapi.user;

public interface IUserService {

	public void save(User user);
	public User findByName(String name);
	public User findByEmail(String email);
	public User findById(Integer id);
	
}
