package com.leandroinacio.picmeapi.user;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leandroinacio.picmeapi.base.BaseEntity;
import com.leandroinacio.picmeapi.picture.Picture;
import com.leandroinacio.picmeapi.role.Role;

import lombok.Data;

@Entity @Table(name = "UserAccount") @Data
public class User extends BaseEntity {

	@Transient @JsonIgnore
	private static final long serialVersionUID = 1L;

	@NotEmpty
	private String email;
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	private String name;
	
	private boolean active;

	@OneToMany @JsonIgnore
	List<Picture> picture;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(unique=false, nullable=true, insertable=true)
	Role role;
}
