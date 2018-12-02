package com.leandroinacio.picmeapi;

import java.util.ArrayList;
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
		Role adm = new Role("Admin", new ArrayList<Permission>(), new ArrayList<User>());
		adm = roleRepository.save(adm);
		
		// Setup permission
		String[] permissions = {"TRAIN_FACES", "USER_FIND", "USER_FIND_ALL", "USER_DELETE", "DELETE_USER_FACES"
				, "SERVE_USER_FACES", "VIEW_USER_FACES", "VIEW_ALL_FACES", "CREATE_FACE"
				, "UPSERT_LOCATION", "FIND_LOCATION", "FIND_ALL_LOCATION", "FIND_USER_LOCATION", "DELETE_USER_LOCATION"
				, "PERMISSION_CRUD", "ROLE_CRUD", "PICTURE_CREATE", "PICTURE_SERVE", "PICTURE_ADD_OWNER", "PICTURE_FIND_PHOTOGRAPHER"
				, "PICTURE_FIND_OWNER", "PICTURE_DELETE", "PICTURE_SEARCH"};
		
		for(String permission : permissions) {
			Permission prm = new Permission(permission, adm);
//			prm = permissionRepository.save(prm);
		
			adm.getPermissions().add(prm);
		}
		
		// Setup user
		User user = new User();
		user.setFirstName("le");
		user.setLastName("le");
		user.setEmail("le@le.com");
		user.setPassword(passwordEnconder.encode("123"));
		user.setActive(true);
		user.setLastPasswordReset(Calendar.getInstance());
		user.setRole(adm);
		
//		User nUser = userRepository.findByEmail("le@le.com");
//		if (nUser == null) {
//			userRepository.save(user);
//		}
		adm.getUser().add(user);
		adm = roleRepository.save(adm);
		
	}
}
