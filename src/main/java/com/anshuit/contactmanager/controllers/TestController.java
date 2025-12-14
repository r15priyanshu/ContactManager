package com.anshuit.contactmanager.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
	@GetMapping("/test")
	public String test(Model model) {
		model.addAttribute("developerName", "ANSHU");
		model.addAttribute("developerSalary", 50000);
		model.addAttribute("developerFavouriteFoods", new String[] { "MAGGI" });
		model.addAttribute("developerMobiles", List.of(98989, 87878, 76767));
		model.addAttribute("developerSkills", Map.of("1", "JAVA", "2", "SPRING BOOT"));
		model.addAttribute("developerIsMarried", false);
		model.addAttribute("developerCountry", null);
		return "test";
	}
}
