package com.leandroinacio.picmeapi.face;

import org.springframework.data.repository.Repository;

public interface IFaceRepository extends Repository<Face, Long>{

	public Face save(Face face);
	public Face findById(Long id);
	public void deleteById(Long id);
	
}
