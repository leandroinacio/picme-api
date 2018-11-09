package com.leandroinacio.picmeapi.picture;

import java.util.BitSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.leandroinacio.picmeapi.model.BaseEntity;

//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Table(name = "Picture")
@Data public class Picture extends BaseEntity {

//	@ManyToOne
//	@JoinColumn(name = "Id")
//	private Photographer photographer;
	
	@NotEmpty
	@Column(name = "Content")
	private BitSet content;
	
	@Column(name = "Description")
	private String description;
	
	@NotEmpty
	@Column(name = "Latitude")
	private Double latitude;
	
	@NotEmpty
	@Column(name = "Longitude")
	private Double longitude;
}
