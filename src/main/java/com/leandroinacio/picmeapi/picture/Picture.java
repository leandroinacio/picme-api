package com.leandroinacio.picmeapi.picture;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import org.springframework.core.io.Resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leandroinacio.picmeapi.base.BaseEntity;
import com.leandroinacio.picmeapi.location.Location;
import com.leandroinacio.picmeapi.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table @AllArgsConstructor @NoArgsConstructor @Data
public class Picture extends BaseEntity {

	@Transient @JsonIgnore
	private static final long serialVersionUID = 1L;

	private String description;

	@NotEmpty
	private String fileType;
		
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(unique=false, nullable=false, insertable=true)
	private User photographer;

	@ManyToMany(fetch=FetchType.EAGER) @OrderColumn
	@JoinColumn(unique=false, nullable=true, insertable=true)
	private List<User> owner;
		
	@ManyToOne(fetch=FetchType.EAGER)
	private Location location;
	
	@Transient @JsonIgnore
	private Resource file;
}
