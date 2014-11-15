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
 * on 15.11.2014.
 */

@Controller
public class DashboardController {
    private static Logger logger = Logger.getLogger(DashboardController.class);
    @Autowired
    IClientService clientService;

    @RequestMapping(value = "/viewDashboard", method = RequestMethod.POST)
    public String viewDashboard(HttpServletRequest req) {
        Session session = Session.getInstance();
        session.setRole(req.getParameter("sessionRole"));
        session.setOn(Boolean.valueOf(req.getParameter("sessionStatus")));
        req.setAttribute("session", session);
        List<Client> clientsList = clientService.getAllClients();
        req.setAttribute("clientsList", clientsList);
        req.setAttribute("pagename", PageName.DASHBOARD.toString());
        logger.info("User " + session.getRole() + " went to view dashboard page.");
        return "operator/dashboard";
    }

    @RequestMapping(value = "/searchClientByNumber", method = RequestMethod.POST)
    public String searchClientByNumber(HttpServletRequest req) {
        Session session = Session.getInstance();
        session.setRole(req.getParameter("sessionRole"));
        session.setOn(Boolean.valueOf(req.getParameter("sessionStatus")));
        req.setAttribute("session", session);
        List<Client> clientsList = null;
        try {
            long number = Util.checkLong(req.getParameter("number"));
            logger.info("User " + session.getRole() + " searching of client by number " + number + ".");
            Client client = clientService.findClientByNumber(number);
            req.setAttribute("client", client);
            req.setAttribute("pagename", PageName.CLIENT.toString());
            req.setAttribute("successmessage", "Client " + client.getName() + " found and loaded from database.");
            logger.info("User " + session.getRole() + " went to client page.");
            return "client/client";
        } catch (ECareException ecx) {
            clientsList = clientService.getAllClients();
            req.setAttribute("clientsList", clientsList);
            req.setAttribute("pagename", PageName.DASHBOARD.toString());
            req.setAttribute("errormessage", ecx.getMessage());
            return "operator/dashboard";
        }
    }

    @RequestMapping(value = "/deleteClient", method = RequestMethod.POST)
    public String deleteClient(HttpServletRequest req) {
        Session session = Session.getInstance();
        session.setRole(req.getParameter("sessionRole"));
        session.setOn(Boolean.valueOf(req.getParameter("sessionStatus")));
        req.setAttribute("session", session);
        long clientId = Long.valueOf(req.getParameter("id"));
        clientService.deleteClient(clientId);
        logger.info("User " + session.getRole() + " deleted are client with id:" + clientId + " from database.");
        List<Client> clientsList  = clientService.getAllClients();
        req.setAttribute("clientsList", clientsList);
        req.setAttribute("pagename", PageName.DASHBOARD.toString());
        logger.info("User " + session.getRole() + " went to view dashboard page.");
        return "operator/dashboard";
    }
}