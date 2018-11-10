package com.leandroinacio.picmeapi.face;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.leandroinacio.picmeapi.base.BaseEntity;
import com.leandroinacio.picmeapi.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table @AllArgsConstructor @NoArgsConstructor
@Data public class Face extends BaseEntity {

	@NotEmpty
	private String fileType;
	
	@ManyToOne
	@JoinColumn(unique=false, nullable=false, insertable=true)
	private User user;
}
