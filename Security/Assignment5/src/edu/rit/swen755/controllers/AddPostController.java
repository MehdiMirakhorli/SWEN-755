package edu.rit.swen755.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.rit.swen755.models.BlogPost;
import edu.rit.swen755.models.User;
import edu.rit.swen755.views.ViewsEnum;

public class AddPostController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(String
				.valueOf(ViewsEnum.POST_ADD));
		rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {

			HttpSession session = request.getSession();
			String username = String.valueOf(session.getAttribute("username"));
			User user = User.get(username);

			BlogPost post = new BlogPost(user.getId(),
					request.getParameter("title"),
					request.getParameter("content"));

			int affectedRows = post.save();
			if (affectedRows > 0)
				response.sendRedirect(ViewsEnum.HOME.getUrl());
		} catch (SQLException e) {
			throw new ServletException(e);
		}

	}
}