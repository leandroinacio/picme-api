package com.leandroinacio.picmeapi.jwt;

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
        return new JwtUser(
        		user.getEmail(),
        		user.getFirstName(),
        		user.getLastName(),
        		user.getEmail(),
        		user.getPassword(),
                mapToGrantedAuthorities(user.getPermissions()),
                user.isActive(),
                user.getLastPasswordReset()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Permission> permissions) {
        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toList());
    }

}
