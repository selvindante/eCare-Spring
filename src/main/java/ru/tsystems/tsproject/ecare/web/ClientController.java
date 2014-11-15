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

/**
 * Created by Selvin
 * on 15.11.2014.
 */

@Controller
public class ClientController {
    @Autowired
    IClientService clientService;
    private static Logger logger = Logger.getLogger(ClientController.class);

    @RequestMapping(value = "/viewClient", method = RequestMethod.POST)
    public String viewClient(HttpServletRequest req) {
        long clientId = Long.valueOf(req.getParameter("id"));
        Session session = Session.getInstance();
        session.setRole(req.getParameter("sessionRole"));
        session.setOn(Boolean.valueOf(req.getParameter("sessionStatus")));
        req.setAttribute("session", session);
        Client client = clientService.loadClient(clientId);
        req.setAttribute("client", client);
        req.setAttribute("pagename", PageName.CLIENT.toString());
        logger.info("User " + session.getRole() + " went to client page.");
        return "client/client";
    }

    @RequestMapping(value = "/editClient", method = RequestMethod.POST)
    public String editClient(HttpServletRequest req) {
        long clientId = Long.valueOf(req.getParameter("id"));
        Session session = Session.getInstance();
        session.setRole(req.getParameter("sessionRole"));
        session.setOn(Boolean.valueOf(req.getParameter("sessionStatus")));
        req.setAttribute("session", session);
        Client client = clientService.loadClient(clientId);
        req.setAttribute("client", client);
        req.setAttribute("pagename", PageName.EDIT_CLIENT.toString());
        logger.info("User " + session.getRole() + " went to edit client page.");
        return "client/editClient";
    }

    @RequestMapping(value = "/updateClient", method = RequestMethod.POST)
    public String updateClient(HttpServletRequest req) {
        long clientId = Long.valueOf(req.getParameter("id"));
        Session session = Session.getInstance();
        session.setRole(req.getParameter("sessionRole"));
        session.setOn(Boolean.valueOf(req.getParameter("sessionStatus")));
        req.setAttribute("session", session);
        Client client = clientService.loadClient(clientId);
        try {
            client.setName(Util.checkStringLength(Util.checkStringOnEmpty(req.getParameter("name"))));
            client.setLastname(Util.checkStringLength(req.getParameter("lastname")));
            client.setBirthDate(Util.checkDate(req.getParameter("birthdate")));
            client.setPassport(Util.checkLong(Util.checkStringOnEmpty(req.getParameter("passport"))));
            client.setAddress(Util.checkStringLength(req.getParameter("address")));
            client = clientService.saveOrUpdateClient(client);
            req.setAttribute("client", client);
            req.setAttribute("pagename", PageName.CLIENT.toString());
            req.setAttribute("successmessage", "Personal info of client " + client.getFullName() + " updated.");
            logger.info("Personal info of client " + client + " updated.");
            return "client/client";
        } catch (ECareException ecx) {
            req.setAttribute("pagename", PageName.EDIT_CLIENT.toString());
            req.setAttribute("errormessage", ecx.getMessage());
            return "client/editClient";
        }
    }

    @RequestMapping(value = "/addAmount", method = RequestMethod.POST)
    public String addAmount(HttpServletRequest req) {
        long clientId = Long.valueOf(req.getParameter("id"));
        Session session = Session.getInstance();
        session.setRole(req.getParameter("sessionRole"));
        session.setOn(Boolean.valueOf(req.getParameter("sessionStatus")));
        req.setAttribute("session", session);
        Client client = clientService.loadClient(clientId);
        try {
            int amount = Util.checkInt(req.getParameter("amount"));
            client.addAmount(amount);
            client = clientService.saveOrUpdateClient(client);
            req.setAttribute("client", client);
            req.setAttribute("pagename", PageName.CLIENT.toString());
            req.setAttribute("successmessage", "Amount " + amount + " added to balance of client " + client.getFullName() + ".");
            logger.info("User " + session.getRole() + " added amount to balance of client " + client + ".");
            return "client/client";
        } catch (ECareException ecx) {
            req.setAttribute("pagename", PageName.CLIENT.toString());
            req.setAttribute("errormessage", ecx.getMessage());
            return "client/client";
        }
    }

    @RequestMapping(value = "/newContract", method = RequestMethod.POST)
    public String newContract(HttpServletRequest req) {
        long clientId = Long.valueOf(req.getParameter("id"));
        Session session = Session.getInstance();
        session.setRole(req.getParameter("sessionRole"));
        session.setOn(Boolean.valueOf(req.getParameter("sessionStatus")));
        Client client = clientService.loadClient(clientId);
        req.setAttribute("client", client);
        req.setAttribute("session", session);
        req.setAttribute("pagename", PageName.NEW_CONTRACT.toString());
        logger.info("User " + session.getRole() + " went to create contract page.");
        return "client/createContract";
    }
}
