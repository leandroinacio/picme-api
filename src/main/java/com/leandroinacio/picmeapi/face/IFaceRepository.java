package com.leandroinacio.picmeapi.face;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.leandroinacio.picmeapi.user.User;

public interface IFaceRepository extends Repository<Face, Long>{

	public Face save(Face face);
	public Page<Face> findAll(Pageable pageable);
	public Face findById(Long id);
	public Page<Face> findByUser(User user, Pageable pageable);
	public void deleteById(Long id);
	
}
