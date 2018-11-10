package com.leandroinacio.picmeapi.base;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leandroinacio.picmeapi.utils.DateUtils;

import lombok.Data;

@MappedSuperclass
@Data public class BaseEntity implements Serializable {

	@Transient @JsonIgnore
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Id	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;		
	
	@NotNull @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = DateUtils.DEFAULT_FORMAT)
	private Calendar createDate;
	
	@NotNull @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = DateUtils.DEFAULT_FORMAT)
	private Calendar modifiedDate;
    
    @PrePersist
    protected void onCreate() {
      this.createDate = Calendar.getInstance();
      this.modifiedDate = Calendar.getInstance();
    }

    @PreUpdate
    protected void onUpdate() {
      this.modifiedDate = Calendar.getInstance();
    }
	
}
