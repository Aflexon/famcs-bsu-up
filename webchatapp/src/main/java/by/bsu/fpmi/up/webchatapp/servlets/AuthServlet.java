package by.bsu.fpmi.up.webchatapp.servlets;

import by.bsu.fpmi.up.webchatapp.models.User;
import by.bsu.fpmi.up.webchatapp.storages.UserStorage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet(value = "/login", initParams = {
        @WebInitParam(name = "cookie-live-time", value = "300")
})
public class AuthServlet extends HttpServlet {
    public static final String COOKIE_USER_ID = "uid";

    private int cookieLifeTime = -1;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cookieLifeTime = Integer.parseInt(config.getInitParameter("cookie-live-time"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            UserStorage userStorage = UserStorage.getInstance();
            req.setAttribute("login", login);
            if (userStorage.checkUser(login, password)) {
                User user = userStorage.getUserByLogin(login);
                Cookie userIdCookie = new Cookie(COOKIE_USER_ID, user.getUid());
                userIdCookie.setMaxAge(cookieLifeTime);
                resp.addCookie(userIdCookie);
                resp.sendRedirect("/homepage.html");
            } else {
                req.setAttribute("error", "These credentials do not match our records.");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
                dispatcher.forward(req, resp);
            }
        } catch (NoSuchAlgorithmException e) {
            resp.sendError(500, e.getMessage());
        }
    }
}
