package com.anshuit.contactmanager.helper;

import com.anshuit.contactmanager.entities.User;

import jakarta.servlet.http.HttpSession;

public class ValidUserCheck {

	public static boolean check(HttpSession session, String username) {
		User user = (User) session.getAttribute("loggedInUser");
		return user.getUsername().equals(username);
	}
}
