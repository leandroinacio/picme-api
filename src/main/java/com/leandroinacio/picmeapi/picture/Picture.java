package com.leandroinacio.picmeapi.picture;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.leandroinacio.picmeapi.base.BaseEntity;
import com.leandroinacio.picmeapi.user.User;
import com.leandroinacio.picmeapi.utils.DateUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity @Table @AllArgsConstructor
@Data public class Picture extends BaseEntity {

	private String description;

	@NotEmpty
	private String fileType;
	
	@NotEmpty
	private String country;

	@NotEmpty
	private String state;
		
	@NotEmpty
	private String city;

	private String reference1;
	private String reference2;
	
	@ManyToOne
	@NotEmpty @JoinColumn(unique=false, nullable=false, insertable=true)
	private User photographer;

	@ManyToMany
	@NotEmpty @JoinColumn(unique=false, nullable=true, insertable=true)
	private List<User> owner;
	
	@ManyToMany
	@NotEmpty @JoinColumn(unique=false, nullable=true, insertable=true)
	private List<User> faceMatch;
	
	@NotNull @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = DateUtils.DEFAULT_FORMAT)
	private Calendar pictureDate;
}
