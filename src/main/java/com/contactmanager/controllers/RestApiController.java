package com.contactmanager.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contactmanager.entities.Contact;
import com.contactmanager.entities.User;
import com.contactmanager.repos.ContactRepository;
import com.contactmanager.repos.UserRepository;
import com.contactmanager.services.DashboardService;

@RestController
public class RestApiController {
	
	@Autowired
	private DashboardService dashboardService;

	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/try")
	public Optional<User> demo() {
		return userRepository.findById(1);
	}

}
