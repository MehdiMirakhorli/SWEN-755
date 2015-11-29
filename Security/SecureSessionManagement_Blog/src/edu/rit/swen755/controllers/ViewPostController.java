package edu.rit.swen755.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.rit.swen755.models.BlogPost;
import edu.rit.swen755.views.ViewsEnum;
/*
 * Visualizations of a post
 */
public class ViewPostController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			BlogPost post = BlogPost.get(request.getParameter("id"));
			request.setAttribute("post", post);

			RequestDispatcher rd = request.getRequestDispatcher(String
					.valueOf(ViewsEnum.POST));
			rd.forward(request, response);
		} catch (SQLException e) {
			throw new ServletException(e);
		}

	}
}