package com.leandroinacio.picmeapi.picture;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leandroinacio.picmeapi.base.BaseEntity;
import com.leandroinacio.picmeapi.location.Location;
import com.leandroinacio.picmeapi.user.User;
import com.leandroinacio.picmeapi.utils.DateUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table @AllArgsConstructor @NoArgsConstructor
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
	
	@ManyToOne @JsonIgnore
	@JoinColumn(unique=false, nullable=false, insertable=true)
	private User photographer;

	@ManyToMany @JsonIgnore
	@JoinColumn(unique=false, nullable=true, insertable=true)
	private List<User> owner;
	
	@ManyToMany @JsonIgnore
	@JoinColumn(unique=false, nullable=true, insertable=true)
	private List<User> faceMatch;
	
	// TODO: Talvez eu nao precise deste campo
	@NotNull @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = DateUtils.DEFAULT_FORMAT)
	private Calendar pictureDate;
	
	@OneToOne @JsonIgnore
	private Location location;
	
	@Transient @JsonIgnore
	private Resource file;
}
