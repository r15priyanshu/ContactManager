package com.contactmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contactmanager.entities.User;
import com.contactmanager.repos.UserRepository;

@Service
public class RegisterService {
	@Autowired
	private UserRepository userRepository;
	
	public boolean registerUser(User user)
	{
		try{
			userRepository.save(user);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	public User findUserByEmail(String email)
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
