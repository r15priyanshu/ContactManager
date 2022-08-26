package com.contactmanager.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.contactmanager.entities.User;
import com.contactmanager.helper.Message;

@Component
public class DashboardInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//System.out.println("INSIDE INTERCEPTOR");
		User user=(User) request.getSession().getAttribute("loggedInUser");
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
