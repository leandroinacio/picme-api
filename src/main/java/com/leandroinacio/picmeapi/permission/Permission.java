package com.leandroinacio.picmeapi.permission;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leandroinacio.picmeapi.base.BaseEntity;
import com.leandroinacio.picmeapi.user.User;

import lombok.Data;

@Entity @Table @Data 
public class Permission extends BaseEntity {

	@Transient @JsonIgnore
	private static final long serialVersionUID = 1L;

	@NotEmpty
	private String name;
	
	@ManyToMany @JsonIgnore
	List<User> user;
		
}
