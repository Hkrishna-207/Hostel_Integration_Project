package com.hstls.intigration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	//this bean is what the controller uses to encrypt the password
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
			.authorizeHttpRequests(auth -> auth
					.requestMatchers("/login","/log","/register").permitAll()
					.anyRequest().authenticated()
				)
			.formLogin(form -> form
					.loginPage("/log")
					.defaultSuccessUrl("/userDashboard",true)
					.permitAll()
				)
			.logout(logout -> logout.logoutSuccessUrl("/log?logout")
			);
		return http.build();
	}
}
