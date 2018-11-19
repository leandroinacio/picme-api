package com.leandroinacio.picmeapi.location;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leandroinacio.picmeapi.base.BaseEntity;
import com.leandroinacio.picmeapi.picture.Picture;
import com.leandroinacio.picmeapi.user.User;

import lombok.Data;

@Entity @Table
@Data public class Location extends BaseEntity {

	@NotEmpty
	private Calendar locationStartDate;
	
	@NotEmpty
	private Calendar locationEndDate;

	@OneToOne @JsonIgnore
	@JoinColumn(unique=false, nullable=true, insertable=true)
	private User user;

	@OneToOne @JsonIgnore
	@JoinColumn(unique=false, nullable=true, insertable=true)
	private Picture picture;
	
	@NotEmpty
	private String country;
	
	@NotEmpty
	private String state;
	
	@NotEmpty
	private String city;
	
	private String reference1;
	private String reference2;
	
}
