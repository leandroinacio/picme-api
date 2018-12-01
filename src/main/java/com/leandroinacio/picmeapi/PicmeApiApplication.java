package com.leandroinacio.picmeapi;

import java.util.Arrays;
import java.util.Calendar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.leandroinacio.picmeapi.permission.IPermissionRepository;
import com.leandroinacio.picmeapi.permission.Permission;
import com.leandroinacio.picmeapi.role.IRoleRepository;
import com.leandroinacio.picmeapi.role.Role;
import com.leandroinacio.picmeapi.user.IUserRepository;
import com.leandroinacio.picmeapi.user.User;

@SpringBootApplication
//@EnableScheduling
public class PicmeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PicmeApiApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(IUserRepository userRepository, PasswordEncoder passwordEncoder
			, IRoleRepository roleRepository, IPermissionRepository permissionRepository) {
		return args -> {
			initUser(userRepository, passwordEncoder, roleRepository, permissionRepository);
		};
	}
	
	private void initUser(IUserRepository userRepository, PasswordEncoder passwordEnconder
			, IRoleRepository roleRepository, IPermissionRepository permissionRepository) {
		
		// Setup admin role
		Role adm = new Role("Admin", null, null);
		roleRepository.save(adm);
		
		// Setup permission
		Permission prm = new Permission("CREATE_FACE", Arrays.asList(adm));
		permissionRepository.save(prm);
		
		adm.setPermissions(Arrays.asList(prm));
		roleRepository.save(adm);
		
		// Setup user
		User user = new User();
		user.setFirstName("le");
		user.setLastName("le");
		user.setEmail("le@le.com");
		user.setPassword(passwordEnconder.encode("123"));
		user.setActive(true);
		user.setLastPasswordReset(Calendar.getInstance());
		user.setRole(adm);
		
		User nUser = userRepository.findByEmail("le@le.com");
		if (nUser == null) {
			userRepository.save(user);
		}
	}
}
