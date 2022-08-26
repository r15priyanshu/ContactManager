package com.contactmanager.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.contactmanager.entities.User;
import com.contactmanager.helper.Message;
import com.contactmanager.services.RegisterService;

@Controller
public class RegisterController {

	@Autowired
	private RegisterService registerService;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model m) {
		// trying to fetch user if it is in flash attribute
		User user = (User) m.asMap().get("user");
		if (user == null) {
			user = new User();
		}
		m.addAttribute("user", user);
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute User user, Model m, RedirectAttributes redirectAttributes,
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
