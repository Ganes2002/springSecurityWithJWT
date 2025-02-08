package com.cardiac.authenticate.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardiac.authenticate.model.User;
import com.cardiac.authenticate.repository.AuthenticateRepository;

@Service
public class AuthenticateServiceImpl implements AuthenticateService{
	
	@Autowired
	AuthenticateRepository repo;

	@Override
	public User getUserDetails(String username) {
		
		Optional<User> user = repo.getByUsername(username);
		if(user.isPresent()) {
			return user.get();
		}else {
			return null;
		}
		
	}

	
	@Override
	public User addUserDetails(User user) {
		
		User userObject = repo.save(user);
		return userObject;
		
	}

}
