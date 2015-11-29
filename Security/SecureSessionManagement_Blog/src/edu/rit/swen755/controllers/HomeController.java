package edu.rit.swen755.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.rit.swen755.models.BlogPost;
import edu.rit.swen755.views.ViewsEnum;

/**
 * 
 * Controller for showing the initial Web page with a list of blog posts.
 * 
 * @author Joanna
 *
 */
public class HomeController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher(String
				.valueOf(ViewsEnum.HOME));

		try {
			List<BlogPost> list = BlogPost.getAll();
			request.setAttribute("list", list);
			rd.forward(request, response);
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}
}
