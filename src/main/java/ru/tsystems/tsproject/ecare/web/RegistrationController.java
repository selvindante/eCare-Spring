package ru.tsystems.tsproject.ecare.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.tsystems.tsproject.ecare.ECareException;
import ru.tsystems.tsproject.ecare.Session;
import ru.tsystems.tsproject.ecare.entities.Client;
import ru.tsystems.tsproject.ecare.service.IClientService;
import ru.tsystems.tsproject.ecare.util.PageName;
import ru.tsystems.tsproject.ecare.util.Util;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Selvin
 * on 14.11.2014.
 */

@Controller
public class RegistrationController {
    private static Logger logger = Logger.getLogger(RegistrationController.class);

    @Autowired
    IClientService clientService;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(Model model) {
        model.addAttribute("name", "");
        model.addAttribute("lastname", "");
        model.addAttribute("birthdate", "");
        model.addAttribute("passport", "");
        model.addAttribute("address", "");
        model.addAttribute("email", "");
        model.addAttribute("pagename", PageName.REGISTRATION.toString());
        logger.info("New user has enter to the registration page.");
        return "registration";
    }

    @RequestMapping(value = "/addClient", method = RequestMethod.POST)
    public String addClient(HttpServletRequest req) {
        try {
            String name = Util.checkStringLength(Util.checkStringOnEmpty(req.getParameter("name")));
            String lastname = Util.checkStringLength(req.getParameter("lastname"));
            Date birthDate = Util.checkDate(req.getParameter("birthdate"));
            long passport = Util.checkLong(Util.checkStringOnEmpty(req.getParameter("passport")));
            String address = Util.checkStringLength(req.getParameter("address"));
            String email = Util.checkLoginOnExisting(Util.checkStringLength(Util.checkStringOnEmpty(req.getParameter("email"))));
            String password = Util.checkPassword(Util.checkStringOnEmpty(req.getParameter("password1")), Util.checkStringOnEmpty(req.getParameter("password2")));

            Client client = new Client(name, lastname, birthDate, passport, address, email, password, "client", 0);
            client = clientService.saveOrUpdateClient(client);
            req.setAttribute("client", client);

            Session session = Session.getInstance();
            session.setRole("client");
            session.setOn(true);
            req.setAttribute("session", session);
            req.setAttribute("pagename", PageName.CLIENT.toString());
            req.setAttribute("successmessage", "Client " + client.getName() + " created and saved in database.");

            logger.info("New user(client): " + client + " has registered in application.");

            return "client/client";
        } catch (ECareException ecx) {
            req.setAttribute("name", req.getParameter("name"));
            req.setAttribute("lastname", req.getParameter("lastname"));
            req.setAttribute("birthdate", req.getParameter("birthdate"));
            req.setAttribute("passport", req.getParameter("passport"));
            req.setAttribute("address", req.getParameter("address"));
            req.setAttribute("email", req.getParameter("email"));
            req.setAttribute("pagename", PageName.REGISTRATION.toString());
            req.setAttribute("errormessage", ecx.getMessage());
            return "registration";
        }
    }
}
