package edu.rit.swen755.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.rit.swen755.models.User;
import edu.rit.swen755.views.ViewsEnum;
/**
 * Controller of the Login Web page
 * @author Joanna
 *
 */
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// if it is already logged in, redirects to initial page
		if (request.getSession().getAttribute("username") != null) {
			response.sendRedirect(ViewsEnum.HOME.getUrl());
		} else {
			// otherwise shows login form
			String loginUrl = ViewsEnum.LOGIN.toString();
			RequestDispatcher rd = request.getRequestDispatcher(loginUrl);
			request.setAttribute("error_msg", "");
			rd.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		try {
			if (username != null && password != null
					&& User.isLoginValid(username, password)) {
				HttpSession s = request.getSession();
				s.setAttribute("username", username);
				response.sendRedirect(ViewsEnum.HOME.getUrl());
				return;
			} else {
				String loginUrl = ViewsEnum.LOGIN.toString();
				String errorMsg = "Username/password invalid(s). Try again";
				RequestDispatcher rd = request.getRequestDispatcher(loginUrl);
				request.setAttribute("error_msg", errorMsg);
				rd.forward(request, response);
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}

	}
}