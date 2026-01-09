package com.hstls.intigration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.hstls.intigration.component.CustomSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	//this bean is what the controller uses to encrypt the password
	
	@Autowired
	private CustomSuccessHandler successHandler;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
			.authorizeHttpRequests(auth -> auth
					.requestMatchers("/login","/log","/register").permitAll()
					.requestMatchers("/admin/**").hasRole("ADMIN")
					.requestMatchers("/user/**").hasRole("USER")
					.anyRequest().authenticated()
				)
			.formLogin(form -> form
					.loginPage("/log")
					.successHandler(successHandler)
					.permitAll()
				)
			.logout(logout -> logout.logoutSuccessUrl("/")
			);
		return http.build();
	}
}
