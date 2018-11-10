package com.leandroinacio.picmeapi.picture;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.leandroinacio.picmeapi.base.BaseEntity;
import com.leandroinacio.picmeapi.user.User;

import lombok.Data;

@Entity @Table
@Data public class Picture extends BaseEntity {

	private String description;

	@NotEmpty
	private String fileType;
	
	@NotEmpty
	private Double latitude;
	
	@NotEmpty
	private Double longitude;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@NotEmpty @JoinColumn
	private User photographer;

	@ManyToMany(cascade = CascadeType.ALL)
	@NotEmpty @JoinColumn
	private List<User> owner;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@NotEmpty @JoinColumn
	private List<User> faceMatch;
}
