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
import com.anshuit.contactmanager.services.LoginService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;

	@GetMapping("/login")
	public String login(Model model) {
		// trying to fetch user if it is in flash attribute
		AppUser user = (AppUser) model.asMap().get("user");
		if (user == null) {
			user = new AppUser();
		}
		model.addAttribute("user", user);
		return "login";
	}

	@PostMapping("/login")
	public String doLogin(@ModelAttribute AppUser user, Model model, RedirectAttributes redirectAttributes,
			HttpSession session) {
		System.out.println("Login requested for :" + user.getEmail());

		if (loginService.findUserByEmail(user.getEmail()) != null) {
			AppUser founduser = loginService.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
			if (founduser != null) {
				session.setAttribute("loggedInUser", founduser);
				System.out.println("Login Successful for :" + user.getEmail());
				return "redirect:/user/" + founduser.getUsername() + "/profile";
			} else {
				Message message = new Message("Incorrect Password !!", "alert-danger");
				session.setAttribute("message", message);
			}
		} else {
			Message message = new Message("Email not Registered !!", "alert-danger");
			session.setAttribute("message", message);
		}
		System.out.println("at last");
		redirectAttributes.addFlashAttribute("user", user);
		return "redirect:/login";
	}

	@GetMapping("/logout")
	public String logout(Model model, HttpSession session) {
		if (session.getAttribute("loggedInUser") != null) {
			session.removeAttribute("loggedInUser");
			Message message = new Message("Logged Out Successfully !!", "alert-success");
			session.setAttribute("message", message);
		} else {
			Message message = new Message("You need to Login First !!", "alert-danger");
			session.setAttribute("message", message);
		}
		return "redirect:/login";
	}
}
