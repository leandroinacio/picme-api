package com.leandroinacio.picmeapi.base;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leandroinacio.picmeapi.utils.DateUtils;

import lombok.Data;

@MappedSuperclass @Data 
public class BaseEntity implements Serializable {

	@Transient @JsonIgnore
	private static final long serialVersionUID = 1L;
	
	@Transient @JsonIgnore
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;		
	
	@Temporal(TemporalType.TIMESTAMP) @CreationTimestamp
    @DateTimeFormat(pattern = DateUtils.DEFAULT_FORMAT)
	@Column(updatable=false)
	private Calendar createDate;
	
	@Temporal(TemporalType.TIMESTAMP) @UpdateTimestamp
    @DateTimeFormat(pattern = DateUtils.DEFAULT_FORMAT)
	private Calendar modifiedDate;
    
}
