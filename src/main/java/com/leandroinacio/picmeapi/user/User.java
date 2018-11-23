package com.leandroinacio.picmeapi.user;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leandroinacio.picmeapi.base.BaseEntity;
import com.leandroinacio.picmeapi.picture.Picture;

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

	@OneToMany @JsonIgnore
	List<Picture> picture;
}
