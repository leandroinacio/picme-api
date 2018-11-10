package com.leandroinacio.picmeapi.face;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.leandroinacio.picmeapi.model.BaseEntity;
import com.leandroinacio.picmeapi.user.User;

import lombok.Data;

@Entity @Table
@Data public class Face extends BaseEntity {

	@NotEmpty
	private String fileType;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private User user;
}
