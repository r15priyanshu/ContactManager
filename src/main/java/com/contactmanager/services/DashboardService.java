package com.contactmanager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.contactmanager.entities.Contact;
import com.contactmanager.entities.User;
import com.contactmanager.repos.ContactRepository;
import com.contactmanager.repos.UserRepository;

@Service
public class DashboardService {
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public boolean addContact(Contact contact)
	{
		if(contactRepository.save(contact)!=null) {
			return true;
		}else {
			return false;
		}
	}
	
	public Page<Contact> findContactsByUser(User user,Pageable pageable)
	{
		return contactRepository.findContactsByUser(user.getUid(),pageable);
	}
	
	public Contact findContactById(int cid) 
	{
		//System.out.println(contactRepository.findById(cid).get());
		return contactRepository.findById(cid).get();
	}
	
	public void deleteContactById(int cid)
	{
		contactRepository.deleteById(cid);
	}
	
	public User findUserByUsername(String username)
	{
		return userRepository.findUserByUsername(username);
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
	
	public User editUser(User user)
	{
		return userRepository.save(user);
	}
	
	public int updateProfilePic(int userid,String filename) {
		return userRepository.updateProfilePic(userid, filename);
	}
	
	public DashboardService() {
	}
}
