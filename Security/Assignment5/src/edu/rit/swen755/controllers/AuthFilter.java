package edu.rit.swen755.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.rit.swen755.models.User;
import edu.rit.swen755.views.ViewsEnum;
/**
 * Filter that implements Authentication and Auhtorization
 * @author Joanna
 *
 */
public class AuthFilter implements Filter {

	public void init(FilterConfig filterConfig) {
		// Nothing to do here
	}

	/**
	 * Invoked when request is captured by the filter
	 */
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		try {

			if (!isLoggedIn(request)) {
				// redirect to login page
				response.sendRedirect(ViewsEnum.LOGIN.getUrl());
				return;
			}

			if (!isAuthorized(request)) {
				// redirect to unauthorization error page 
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}

			// if we reach here: user is authorized to perform the task
			chain.doFilter(request, response);
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	/**
	 * True if user is authenticated
	 * 
	 * @return true when user is authenticated
	 */
	private boolean isLoggedIn(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return session.getAttribute("username") != null;
	}

	/**
	 * Logic to accept or reject access to the page depending on the user's
	 * role.
	 * 
	 * @return true when user is authorized
	 * @throws SQLException
	 *             user could not be retrieved from DB
	 */
	private boolean isAuthorized(HttpServletRequest request)
			throws SQLException {
		String url = new String(request.getRequestURL());
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		// if it is authenticated, then checks user's role
		User user = User.get(username);

		System.out.println("user.isAdmin() " + user.isAdmin());
		System.out.println("user.isReader() " + user.isReader());

		// ADD POST page: only admins authorized
		if (url.contains(ViewsEnum.POST_ADD.getUrl())) {
			return user.isAdmin();
		}

		// OTHER pages: only authenticated users (readers or admins)
		return user.isReader() || user.isAdmin();
	}

	@Override
	public void destroy() {
		// Nothing to do here
	}

}