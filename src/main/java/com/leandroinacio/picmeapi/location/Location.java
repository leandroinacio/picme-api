package com.leandroinacio.picmeapi.location;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.leandroinacio.picmeapi.base.BaseEntity;
import com.leandroinacio.picmeapi.user.User;

import lombok.Data;

@Entity @Table
@Data public class Location extends BaseEntity {

	@NotEmpty
	private Calendar locationDate;
	
	@ManyToOne
	@JoinColumn(unique=false, nullable=false, insertable=true)
	private User user;
	
	@NotEmpty
	private String country;
	
	@NotEmpty
	private String state;
	
	@NotEmpty
	private String city;
	
	private String reference1;
	private String reference2;
}
