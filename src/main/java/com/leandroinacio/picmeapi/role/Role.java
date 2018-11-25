package com.leandroinacio.picmeapi.role;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leandroinacio.picmeapi.base.BaseEntity;
import com.leandroinacio.picmeapi.user.User;

import lombok.Data;

@Entity @Table @Data 
public class Role extends BaseEntity {

	@Transient @JsonIgnore
	private static final long serialVersionUID = 1L;

	@NotEmpty
	private String name;
	
	@OneToMany @JsonIgnore
	List<User> picture;
		
}
