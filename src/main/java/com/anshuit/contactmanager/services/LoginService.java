package com.anshuit.contactmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anshuit.contactmanager.entities.AppUser;
import com.anshuit.contactmanager.repos.UserRepository;

@Service
public class LoginService {
	
	@Autowired
	private UserRepository userRepository;
	
	public AppUser findUserByEmail(String email)
	{
		return userRepository.findUserByEmail(email);
	}
	
	public AppUser findUserByEmailAndPassword(String email,String password)
	{
		return userRepository.findUserByEmailAndPassword(email,password);
	}
}
