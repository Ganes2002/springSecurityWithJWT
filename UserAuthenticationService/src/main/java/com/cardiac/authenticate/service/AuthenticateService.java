package com.cardiac.authenticate.service;

import com.cardiac.authenticate.model.User;

public interface AuthenticateService {
	
	public User getUserDetails(String username);
	public User addUserDetails(User user);

}
