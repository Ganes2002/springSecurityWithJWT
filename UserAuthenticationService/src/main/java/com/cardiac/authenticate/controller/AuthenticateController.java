package com.cardiac.authenticate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardiac.authenticate.exception.UsernameNotFoundException;
import com.cardiac.authenticate.model.User;
import com.cardiac.authenticate.service.AuthenticateService;
import com.cardiac.authenticate.service.JwtService;


@RestController
@RequestMapping("/user")
public class AuthenticateController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	AuthenticateService serv;
	
	@Autowired
	JwtService jwtService;
	
	@PostMapping("/add")
	public ResponseEntity<?> addUser(@RequestBody User user){
		
		User details = serv.addUserDetails(user);
		
		if(details != null) {
			return new ResponseEntity<User>(details,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("User Not Added",HttpStatus.CONFLICT);
		}
		
	}
	
	@GetMapping("/get/{username}")
	public ResponseEntity<?> getUser(@PathVariable String username){
		
		User details = serv.getUserDetails(username);
		
		if(details != null) {
			return new ResponseEntity<User>(details,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("User Not Found",HttpStatus.CONFLICT);
		}
		
	}
	
	@PostMapping("/login")
	public String authenticate(@RequestBody User authRequest) throws UsernameNotFoundException
	{
			
		Authentication authenticate = authenticationManager
		.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		
		if(authenticate.isAuthenticated())
		{
			return jwtService.generateToken(authRequest.getUsername());
		}
		else
		{
			throw new UsernameNotFoundException("invalid Credentials");
		}
	}

}
