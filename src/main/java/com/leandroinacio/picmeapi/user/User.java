package com.leandroinacio.picmeapi.user;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
import com.leandroinacio.picmeapi.jwt.JwtUser;
import com.leandroinacio.picmeapi.location.Location;
import com.leandroinacio.picmeapi.picture.Picture;
import com.leandroinacio.picmeapi.role.Role;
import com.leandroinacio.picmeapi.utils.DateUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name = "UserAccount") @Data
@AllArgsConstructor @NoArgsConstructor
public class User extends BaseEntity {

	@Transient @JsonIgnore
	private static final long serialVersionUID = 1L;

	@NotEmpty @Column(unique=true)
	private String email;
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	private String firstName;
	private String lastName;
	
	private boolean active;

	@OneToMany @JsonIgnore
	List<Picture> pictures;

	@OneToMany @JsonIgnore
	List<Location> locations;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(unique=false,nullable=false,insertable=true)
	Role role;
	
	@Temporal(TemporalType.TIMESTAMP) @NotNull
    @DateTimeFormat(pattern = DateUtils.DEFAULT_FORMAT)
	private Calendar lastPasswordReset;

	public User(JwtUser jwtUser) {
		super();
		this.setId(jwtUser.getId());
		this.setActive(jwtUser.isEnabled());
		this.setCreateDate(jwtUser.getCreateDate());
	}
}
