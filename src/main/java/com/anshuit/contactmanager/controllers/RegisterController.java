package com.anshuit.contactmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.anshuit.contactmanager.entities.AppUser;
import com.anshuit.contactmanager.helper.Message;
import com.anshuit.contactmanager.services.RegisterService;

import jakarta.servlet.http.HttpSession;

@Controller
public class RegisterController {

	@Autowired
	private RegisterService registerService;

	@GetMapping("/register")
	public String register(Model m) {
		// trying to fetch user if it is in flash attribute
		AppUser user = (AppUser) m.asMap().get("user");
		if (user == null) {
			user = new AppUser();
		}
		m.addAttribute("user", user);
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute AppUser user, Model m, RedirectAttributes redirectAttributes,
			HttpSession session) {
		if (registerService.checkUserByEmail(user.getEmail())) {
			Message message = new Message("Email Already Registered !!", "alert-danger");
			session.setAttribute("message", message);
		} else {
			if (registerService.checkUserByUsername(user.getUsername())) {
				Message message = new Message("Username Already Taken !!", "alert-danger");
				session.setAttribute("message", message);
			} else {
				if (registerService.registerUser(user)) {
					Message message = new Message("Email Registered Successfully !!", "alert-success");
					session.setAttribute("message", message);
					return "redirect:/register";
				} else {
					Message message = new Message("Something Went Wrong !!", "alert-danger");
					session.setAttribute("message", message);
				}
			}
		}
		redirectAttributes.addFlashAttribute("user", user);
		return "redirect:/register";
	}
}
