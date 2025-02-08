package com.cardiac.authenticate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cardiac.authenticate.model.User;


@Repository
public interface AuthenticateRepository  extends JpaRepository<User,String>{

	public Optional<User> getByUsername(String username);

}
