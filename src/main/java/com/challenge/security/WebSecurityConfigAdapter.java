package com.challenge.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.challenge.dto.UserCustomDTO;
import com.challenge.entity.User;
import com.challenge.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigAdapter extends WebSecurityConfigurerAdapter{
	
	@Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManagerBean();
    }
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
    
    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository userRepository) throws Exception {
        if (userRepository.count() == 0) {
            User user = new User();
            user.setEmail("bruno@gmail.com");
            user.setPassword("123456");
            user.setFullName("Bruno Mezenga");
            user.setNickname("Mezenga");
            
            userRepository.save(user);
        }

        builder.userDetailsService(email -> new UserCustomDTO(userRepository.findByEmail(email)))
        .passwordEncoder(passwordEncoder());
    }
    
}
