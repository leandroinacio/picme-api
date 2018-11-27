package com.leandroinacio.picmeapi.user;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leandroinacio.picmeapi.base.BaseEntity;
import com.leandroinacio.picmeapi.permission.Permission;
import com.leandroinacio.picmeapi.picture.Picture;
import com.leandroinacio.picmeapi.utils.DateUtils;

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
	private String firstName;
	private String lastName;
	
	private boolean active;

	@OneToMany @JsonIgnore
	List<Picture> pictures;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinColumn(unique=false, nullable=true, insertable=true)
	List<Permission> permissions;
	
	@Temporal(TemporalType.TIMESTAMP) @NotNull
    @DateTimeFormat(pattern = DateUtils.DEFAULT_FORMAT)
	private Calendar lastPasswordReset;
	
}
