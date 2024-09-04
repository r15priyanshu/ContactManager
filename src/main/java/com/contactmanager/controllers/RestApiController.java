package com.contactmanager.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.contactmanager.entities.Contact;
import com.contactmanager.entities.User;
import com.contactmanager.helper.ValidUserCheck;
import com.contactmanager.repos.ContactRepository;
import com.contactmanager.repos.UserRepository;
import com.contactmanager.services.DashboardService;

@RestController
public class RestApiController {
	
	@Autowired
	private ContactRepository contactRepository;
	
	@GetMapping("/api/user/{username}/contacts/{id}/{fullnameparameter}")
	public List<Contact> findContactsById(@PathVariable("id") int id,@PathVariable("username") String username,@PathVariable("fullnameparameter") String fullnameparameter,HttpSession session) {
		return contactRepository.findContactsByUserSearchParameter(id,fullnameparameter);
	}

}
