package edu.rit.swen755.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.rit.swen755.views.ViewsEnum;

/**
 * Controller for logging out of the applications
 */

public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// destroy user's session and redirects to main page
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect(ViewsEnum.LOGIN.getUrl());
	}

}
