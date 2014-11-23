package ru.tsystems.tsproject.ecare.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.tsystems.tsproject.ecare.ECareException;
import ru.tsystems.tsproject.ecare.Session;
import ru.tsystems.tsproject.ecare.entities.Client;
import ru.tsystems.tsproject.ecare.service.IClientService;
import ru.tsystems.tsproject.ecare.util.PageName;
import ru.tsystems.tsproject.ecare.util.Util;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Selvin
 * on 14.11.2014.
 */

@Controller
public class LoginController {
    private static Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    IClientService clientService;

    @RequestMapping(value = "/backToLogin", method = RequestMethod.POST)
    public String backToLogin() {
        return "login";
    }

    @RequestMapping(value = "/showLogin", method = RequestMethod.GET)
    public String showLogin() {
        return "login";
    }

    @RequestMapping(value = "/loginUser", method = RequestMethod.POST)
    public String loginUser(HttpServletRequest req) {
        Session session = Session.getInstance();
        try {
            String login = Util.checkStringLength(req.getParameter("login"));
            String password = Util.checkStringLength(req.getParameter("password"));
            Client client = clientService.findClient(login, password);
            req.setAttribute("role", client.getRole());
            if(client.getRole().equals("client")) {
                session.setRole("client");
                session.setOn(true);
                req.setAttribute("session", session);
                req.setAttribute("client", client);
                req.setAttribute("pagename", PageName.CLIENT.toString());
                req.setAttribute("successmessage", "Client " + client.getName() + " loaded from database.");
                logger.info("User(client): " + client + " login in application.");
                return "client/client";
            }
            else if(client.getRole().equals("admin")){
                List<Client> clientsList = clientService.getAllClients();
                session.setRole("admin");
                session.setOn(true);
                req.setAttribute("session", session);
                req.setAttribute("clientsList", clientsList);
                req.setAttribute("pagename", PageName.DASHBOARD.toString());
                req.setAttribute("successmessage", "Administrator session started.");
                logger.info("User(admin): " + client + " login in application.");
                return "operator/dashboard";
            }
        } catch (ECareException ecx) {
            req.setAttribute("errormessage", ecx.getMessage());
            return "login";
        }
        return "login";
    }

    @RequestMapping(value = "/logoutUser", method = RequestMethod.POST)
    public String logoutUser(HttpServletRequest req) {
        Session session = Session.getInstance();
        session.setOn(false);
        req.setAttribute("session", session);
        logger.info("User has logout from application.");
        req.setAttribute("successmessage", "User has logout from system.");
        return "login";
    }
}
