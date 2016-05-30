package by.bsu.fpmi.up.webchatapp.filters;

import by.bsu.fpmi.up.webchatapp.servlets.AuthServlet;
import by.bsu.fpmi.up.webchatapp.storages.UserStorage;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(value = {"/homepage.html", "/chat"})
public class ChatFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String uidParam = request.getParameter(AuthServlet.COOKIE_USER_ID);
        if (uidParam == null && request instanceof HttpServletRequest ) {
            Cookie[] cookies = ((HttpServletRequest) request).getCookies();
            if(cookies != null){
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(AuthServlet.COOKIE_USER_ID)) {
                        uidParam = cookie.getValue();
                    }
                }
            }
        }
        boolean auhenticated = checkAuthenticated(uidParam);
        if (auhenticated) {
            chain.doFilter(request, response);
        } else if (response instanceof HttpServletResponse) {
            ((HttpServletResponse) response).sendRedirect("/pages/unauthorized.html");
        } else {
            response.getOutputStream().println("403, Forbidden");
        }

    }

    private boolean checkAuthenticated(String uid) {
        return UserStorage.getInstance().getUserByUid(uid) != null;
    }

    @Override
    public void destroy() {

    }
}
