package com.leandroinacio.picmeapi.jwt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.leandroinacio.picmeapi.user.IUserRepository;
import com.leandroinacio.picmeapi.user.User;

@Service
public class JwtUserService implements UserDetailsService {

	private final Log logger = LogFactory.getLog(this.getClass());
	
    @Autowired
    private IUserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
        	
        	logger.info("User tried to login without success with " + username);
        	
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return JwtUserFactory.create(user);
        }
    }
	
}
