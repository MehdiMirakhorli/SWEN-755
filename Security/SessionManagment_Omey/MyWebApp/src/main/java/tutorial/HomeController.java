package tutorial;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class HomeController extends HttpServlet {

    /**
	 * 
	 */
	public static final String VIEW_TEMPLATE_PATH = "/WEB-INF/jsp/home.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(VIEW_TEMPLATE_PATH).forward(req, resp);
    }
}