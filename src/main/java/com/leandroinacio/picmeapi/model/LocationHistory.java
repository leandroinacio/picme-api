//package com.leandroinacio.picmeapi.model;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//import javax.validation.constraints.NotNull;
//
//import com.leandroinacio.picmeapi.user.User;
//
//import lombok.Data;
//
//@Entity
//@Table(name = "LocationHistory")
//@Data public class LocationHistory extends BaseEntity {
//
//	@ManyToOne
//	@JoinColumn(name = "Id")
//	private User user;
//	
//	@Column(name = "Latitude")
//	@NotNull
//	private Double latitude;
//	
//	@Column(name = "Longitude")
//	@NotNull
//	private Double longitude;
//}
