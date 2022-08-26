package com.contactmanager.helper;

import javax.servlet.http.HttpSession;

import com.contactmanager.entities.User;

public class ValidUserCheck {

	public static boolean check(HttpSession session,String username)
	{
		User user=(User) session.getAttribute("loggedInUser");
		return user.getUsername().equals(username); 
	}
}
