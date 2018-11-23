package com.leandroinacio.picmeapi.base;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component @Data
public class BaseResponse {
	
	private Boolean isError;
	private String errorMessage;
	private Map<String, Object> data;

	public BaseResponse() {
		this.isError = false;
	}
	
	public BaseResponse(Object object) {
		this.isError = false;
		this.setData(object);
	}
	
	public BaseResponse(Map<String, Object> data) {
		this.setData(data);
	}
	
	public void setData(Object object) {
		this.data = new HashMap<>();
		this.data.put("results", object);
	}
	
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
}
