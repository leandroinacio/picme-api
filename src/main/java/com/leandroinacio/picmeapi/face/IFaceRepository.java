package com.leandroinacio.picmeapi.face;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.repository.Repository;

public interface IFaceRepository extends Repository<Face, Long>{

	public Face save(Face face);
	public Face findById(Long id);
	public void deleteById(Long id);
	
	public List<Calendar> findCreateDateById(Long id);
}
