package ru.tsystems.tsproject.ecare.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.tsystems.tsproject.ecare.service.ITariffService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * This class contains static util methods for controller logic.
 *
 * @author Starostin Konstantin
 */

@Component
public class ControllerUtil {

    /*Tariff service instance*/
    private static ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring/spring-datasource.xml"});
    private static ITariffService tariffService = (ITariffService) context.getBean("tariffService");

    /**
     * Method checks session of user and set it as attribute of request.
     *
     * @param req input HTTP servlet request.
     */
    public static void setRole(HttpServletRequest req) {
        String role = null;
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            role = authority.getAuthority();
        }
        req.setAttribute("role", role);
    }
}
