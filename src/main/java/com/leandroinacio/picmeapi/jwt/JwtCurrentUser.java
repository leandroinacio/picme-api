package com.leandroinacio.picmeapi.jwt;

import com.leandroinacio.picmeapi.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class JwtCurrentUser {
	private String jwt;
	private User user;
}
