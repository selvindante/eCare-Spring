package ru.tsystems.tsproject.ecare.web;

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
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {

        /*count++;

        String role = null;
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            role = authority.getAuthority();
        }
        System.out.println(role);
        filterChain.doFilter(req, resp);
        if (count > 3 && role.equals("ROLE_ANONYMOUS")) {
            logger.warn("Session of this user has status: off. Redirect on login page.");
            req.getRequestDispatcher("/login").forward(req, resp);
        }
        else {*/
            filterChain.doFilter(req, resp);
        //}

    }

    @Override
    public void destroy() {
    }
}
