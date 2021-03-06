package ru.tsystems.tsproject.ecare.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.tsystems.tsproject.ecare.ECareException;
import ru.tsystems.tsproject.ecare.entities.Client;
import ru.tsystems.tsproject.ecare.service.IClientService;
import ru.tsystems.tsproject.ecare.util.PageName;
import ru.tsystems.tsproject.ecare.util.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/**
 * Controller of login.jsp page.
 *
 * @author Starostin Konstantin
 */
@Controller
public class LoginController {
    private static Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    IClientService clientService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView defaultPage() {

        ModelAndView model = new ModelAndView();
        model.setViewName("login");
        return model;

    }

    @RequestMapping(value = "/backToLogin", method = RequestMethod.POST)
    public String backToLogin() {
        return "login";
    }

    @RequestMapping(value = "/showLogin", method = RequestMethod.GET)
    public String showLogin(@RequestParam(value = "error", required = false) String error, HttpServletRequest req) {
        if (error != null) {
            req.setAttribute("errormessage", "Invalid username or password.");
        }
        return "login";
    }

    @RequestMapping(value = "/loginUser", method = RequestMethod.GET)
    public String loginUser(HttpServletRequest req) {
        String role = null;
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            role = authority.getAuthority();
        }
        req.setAttribute("role", role);
        try {
            if (role.equals(Role.ROLE_USER.toString())) {
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Client client = clientService.findClient(userDetails.getUsername(), userDetails.getPassword());
                req.setAttribute("client", client);
                req.setAttribute("pagename", PageName.CLIENT.toString());
                req.setAttribute("successmessage", "Client " + client.getName() + " loaded from database.");
                logger.info("User(client): " + client + " login in application.");
                return "client/client";
            } else if (role.equals(Role.ROLE_ADMIN.toString())) {
                List<Client> clientsList = clientService.getAllClients();
                req.setAttribute("clientsList", clientsList);
                req.setAttribute("pagename", PageName.DASHBOARD.toString());
                req.setAttribute("successmessage", "Administrator session started.");
                logger.info("User(admin) logged in application.");
                return "operator/dashboard";
            }
        } catch (ECareException ecx) {
            req.setAttribute("errormessage", ecx.getMessage());
            return "login";
        }
        return "login";
    }

    @RequestMapping(value = "/logoutUser", method = RequestMethod.GET)
    public String logoutUser(HttpServletRequest req) {
        logger.info("User has logout from application.");
        req.setAttribute("successmessage", "User has logout from system.");
        return "login";
    }
}
