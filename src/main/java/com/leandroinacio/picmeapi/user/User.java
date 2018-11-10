package com.leandroinacio.picmeapi.user;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.leandroinacio.picmeapi.model.BaseEntity;

import lombok.Data;

@Entity @Table(name = "UserAccount")
@Data public class User extends BaseEntity {

	@NotEmpty
	private String email;
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	private String name;
	
	private boolean active;
}
