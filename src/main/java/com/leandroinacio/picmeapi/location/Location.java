package com.leandroinacio.picmeapi.location;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leandroinacio.picmeapi.base.BaseEntity;
import com.leandroinacio.picmeapi.picture.Picture;
import com.leandroinacio.picmeapi.user.User;
import com.leandroinacio.picmeapi.utils.DateUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table @Data @AllArgsConstructor @NoArgsConstructor
public class Location extends BaseEntity {

	@Transient @JsonIgnore
	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.TIMESTAMP) @NotNull
    @DateTimeFormat(pattern = DateUtils.DEFAULT_FORMAT)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	private Calendar startDate;
	
	@Temporal(TemporalType.TIMESTAMP) @NotNull
    @DateTimeFormat(pattern = DateUtils.DEFAULT_FORMAT)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	private Calendar endDate;

	@ManyToOne @JsonIgnore
	@JoinColumn(unique=false, nullable=true, insertable=true)
	private User user;

	@OneToMany @JsonIgnore
	@JoinColumn(unique=false, nullable=true, insertable=true)
	private List<Picture> picture;
	
	@NotEmpty
	private String country;
	
	@NotEmpty
	private String state;
	
	@NotEmpty
	private String city;
	
	private String reference1;
	private String reference2;
	
}
