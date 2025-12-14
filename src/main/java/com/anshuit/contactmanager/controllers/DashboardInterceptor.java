package com.anshuit.contactmanager.controllers;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.anshuit.contactmanager.entities.AppUser;
import com.anshuit.contactmanager.helper.Message;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class DashboardInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//System.out.println("INSIDE INTERCEPTOR");
		AppUser user=(AppUser) request.getSession().getAttribute("loggedInUser");
		if (user!= null)
			return true;
		else {
			Message message=new Message("You need to Login First !!","alert-danger");
			request.getSession().setAttribute("message", message);
			response.sendRedirect("/login");
			return false;
		}
	}
}
