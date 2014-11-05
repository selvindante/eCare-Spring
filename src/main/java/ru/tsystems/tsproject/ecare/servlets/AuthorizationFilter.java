package ru.tsystems.tsproject.ecare.servlets;

import org.apache.log4j.Logger;
import ru.tsystems.tsproject.ecare.Session;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Selvin
 * on 03.10.2014.
 */
public class AuthorizationFilter implements Filter {
    Session session = null;
    private static Logger logger = Logger.getLogger(AuthorizationFilter.class);
    long count = 0;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        session = Session.getInstance();
        //count = 0;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {

        count++;

        logger.info("Checking of session by filter. Filter call count: " + count + ".");
        String action = req.getParameter("action");
        if(action != null) {
            if(action.equals("login") ||
               action.equals("registration") ||
               action.equals("logout")) {
                filterChain.doFilter(req, resp);
            }
        }
        if (count > 4 && !session.isOn()) {
            logger.warn("Session of this user has status: off. Redirect on login page.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
        else {
            filterChain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {
    }
}
