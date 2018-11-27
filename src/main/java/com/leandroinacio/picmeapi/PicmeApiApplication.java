package com.leandroinacio.picmeapi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.leandroinacio.picmeapi.permission.Permission;
import com.leandroinacio.picmeapi.user.IUserRepository;
import com.leandroinacio.picmeapi.user.User;

@SpringBootApplication
//@EnableScheduling
public class PicmeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PicmeApiApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			initUser(userRepository, passwordEncoder);
		};
	}
	
	private void initUser(IUserRepository userRepository, PasswordEncoder passwordEnconder) {
		User user = new User();
		user.setFirstName("le");
		user.setLastName("le");
		user.setEmail("le@le.com");
		user.setPassword(passwordEnconder.encode("123"));
		user.setActive(true);
		user.setLastPasswordReset(Calendar.getInstance());
//		List<Permission> p = new ArrayList<>();
//		p.add(new Permission());
//		p.get(0).setName("Admin");
//		user.setPermissions(p);
		
		User nUser = userRepository.findByEmail("le@le.com");
		if (nUser == null) {
			userRepository.save(user);
		}
	}
}
