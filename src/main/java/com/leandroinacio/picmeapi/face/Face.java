package com.leandroinacio.picmeapi.face;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import org.springframework.core.io.Resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leandroinacio.picmeapi.base.BaseEntity;
import com.leandroinacio.picmeapi.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table @AllArgsConstructor @NoArgsConstructor @Data 
public class Face extends BaseEntity {
	
	@Transient @JsonIgnore
	private static final long serialVersionUID = 1L;

	@NotEmpty
	private String fileType;
	
	@ManyToOne @JsonIgnore
	@JoinColumn(unique=false, nullable=false, insertable=true)
	private User user;
	
	@Transient @JsonIgnore
	private Resource file;
	
}
