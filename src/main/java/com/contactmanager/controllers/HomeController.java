package com.contactmanager.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.contactmanager.entities.Contact;
import com.contactmanager.entities.User;
import com.contactmanager.repos.UserRepository;

@Controller
public class HomeController {

	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(path = {"/","/home"})
	public String home(Model model)
	{
		model.addAttribute("name","ANSHU");
		model.addAttribute("list",List.of(10,20,30));
		return "home";
	}
	
	@RequestMapping("/test")
	public @ResponseBody User test()
	{	
		User user=new User();
		user.setUsername("anshu12222");
		user.setEmail("anandpriyanshu661222@gmail.com");
		user.setFullname("anshu anand");
		userRepository.save(user);
		return user;
	}
	
	@RequestMapping("/temp")
	public String temp()
	{	
		return "fragments";
	}
}
