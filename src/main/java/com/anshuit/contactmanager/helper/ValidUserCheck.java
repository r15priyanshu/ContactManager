package com.anshuit.contactmanager.helper;

import com.anshuit.contactmanager.entities.AppUser;

import jakarta.servlet.http.HttpSession;

public class ValidUserCheck {

	public static boolean check(HttpSession session, String username) {
		AppUser user = (AppUser) session.getAttribute("loggedInUser");
		return user.getUsername().equals(username);
	}
}
