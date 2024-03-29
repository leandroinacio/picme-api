package com.leandroinacio.picmeapi.jwt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.leandroinacio.picmeapi.permission.Permission;
import com.leandroinacio.picmeapi.user.User;

public class JwtUserFactory {
	
    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
    	List<Permission> permissions = new ArrayList<>();
    	if (user.getRole() != null) {
    		permissions = user.getRole().getPermissions();
    	}
        return new JwtUser(
        		user.getId(),
        		user.getEmail(),
        		user.getFirstName(),
        		user.getLastName(),
        		user.getEmail(),
        		user.getPassword(),
                mapToGrantedAuthorities(permissions),
                user.isActive(),
                user.getLastPasswordReset(),
                user.getCreateDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Permission> permissions) {
        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toList());
    }

}
