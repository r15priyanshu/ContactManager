package com.anshuit.contactmanager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.anshuit.contactmanager.entities.Contact;
import com.anshuit.contactmanager.repos.ContactRepository;

import jakarta.servlet.http.HttpSession;

@RestController
public class RestApiController {
	@Autowired
	private ContactRepository contactRepository;

	@GetMapping("/api/user/{username}/contacts/{id}/{fullnameparameter}")
	public List<Contact> findContactsById(@PathVariable("id") int id, @PathVariable("username") String username,
			@PathVariable("fullnameparameter") String fullnameparameter, HttpSession session) {
		return contactRepository.findContactsByUserSearchParameter(id, fullnameparameter);
	}
}
