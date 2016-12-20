package tutorial;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.directory.CustomData;
import com.stormpath.sdk.lang.Strings;
import com.stormpath.sdk.servlet.account.AccountResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DashboardController extends HttpServlet {

    private static final String VIEW_TEMPLATE_PATH = "/WEB-INF/jsp/dashboard.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String birthday = "";
        String color = "";

        Account account = AccountResolver.INSTANCE.getAccount(req);
        if (account != null) {
            CustomData data = account.getCustomData();
            birthday = (String)data.get("birthday");
            color = (String)data.get("color");
        }

        req.setAttribute("birthday", birthday);
        req.setAttribute("color", color);
        req.getRequestDispatcher(VIEW_TEMPLATE_PATH).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String birthday = req.getParameter("birthday");
        String color = req.getParameter("color");

        //get the currently-logged-in account:
        Account account = AccountResolver.INSTANCE.getAccount(req);
        if (account != null) {

            CustomData data = account.getCustomData();

            if (Strings.hasText(birthday)) {
                data.put("birthday", birthday);
            } else {
                data.remove("birthday");
            }

            if (Strings.hasText(color)) {
                data.put("color", color);
            } else {
                data.remove("color");
            }

            data.save();
        }

        req.setAttribute("birthday", birthday);
        req.setAttribute("color", color);
        req.getRequestDispatcher(VIEW_TEMPLATE_PATH).forward(req, resp);
    }
}