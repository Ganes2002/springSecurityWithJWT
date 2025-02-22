package com.cardiac.authenticate.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cardiac.authenticate.filter.JwtFilter;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	JwtFilter jwtFilter;
	
	 @Bean
	    public PasswordEncoder passwordEncoder1() {
	        return new BCryptPasswordEncoder(); // Ensure BCryptPasswordEncoder is used
	    }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception
	{
		httpSecurity
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(authz -> authz
					.requestMatchers("/user/add", "/user/login")
					.permitAll()
					.anyRequest().authenticated())
			.sessionManagement(session -> session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					).authenticationProvider(authenticationProvider())
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
		return httpSecurity.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
		daoProvider.setUserDetailsService(userDetailsService());
		daoProvider.setPasswordEncoder(passwordEncoder1());
		
		return daoProvider;
	}
	
	@Bean
	public AuthenticationManager getAuth(AuthenticationConfiguration config) throws Exception
	{
		return config.getAuthenticationManager();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new MyUserDetailsService();
	}


}
