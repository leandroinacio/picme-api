package com.leandroinacio.picmeapi.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class JwtCurrentUser {
	private String jwt;
}
