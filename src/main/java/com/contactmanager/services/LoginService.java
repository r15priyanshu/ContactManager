package com.contactmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contactmanager.entities.User;
import com.contactmanager.repos.UserRepository;

@Service
public class LoginService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User findUserByEmail(String email)
	{
		return userRepository.findUserByEmail(email);
	}
	
	public User findUserByEmailAndPassword(String email,String password)
	{
		return userRepository.findUserByEmailAndPassword(email,password);
	}
}
