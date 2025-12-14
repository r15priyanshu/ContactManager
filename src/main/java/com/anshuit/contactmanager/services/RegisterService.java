package com.anshuit.contactmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anshuit.contactmanager.entities.AppUser;
import com.anshuit.contactmanager.repos.UserRepository;

@Service
public class RegisterService {
	@Autowired
	private UserRepository userRepository;
	
	public boolean registerUser(AppUser user)
	{
		try{
			userRepository.save(user);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	public AppUser findUserByEmail(String email)
	{
		return userRepository.findUserByEmail(email);
	}
	
	public boolean checkUserByEmail(String email)
	{
		if(userRepository.findUserByEmail(email)!=null)
			return true;
		else
			return false;
	}
	
	public boolean checkUserByUsername(String username)
	{
		if(userRepository.findUserByUsername(username)!=null)
			return true;
		else
			return false;
	}
		
}
