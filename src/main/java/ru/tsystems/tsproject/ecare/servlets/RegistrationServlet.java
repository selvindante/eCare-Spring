package ru.tsystems.tsproject.ecare.servlets;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.tsystems.tsproject.ecare.ECareException;
import ru.tsystems.tsproject.ecare.Session;
import ru.tsystems.tsproject.ecare.entities.Client;
import ru.tsystems.tsproject.ecare.service.IClientService;
import ru.tsystems.tsproject.ecare.util.PageName;
import ru.tsystems.tsproject.ecare.util.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Selvin
 * on 14.10.2014.
 */

@Controller
public class RegistrationServlet extends HttpServlet {

    @Autowired
    IClientService clientService;

    private static Logger logger = Logger.getLogger(RegistrationServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

            req.getRequestDispatcher("/client.jsp").forward(req, resp);
        } catch (ECareException ecx) {
            req.setAttribute("name", req.getParameter("name"));
            req.setAttribute("lastname", req.getParameter("lastname"));
            req.setAttribute("birthdate", req.getParameter("birthdate"));
            req.setAttribute("passport", req.getParameter("passport"));
            req.setAttribute("address", req.getParameter("address"));
            req.setAttribute("email", req.getParameter("email"));
            req.setAttribute("pagename", PageName.REGISTRATION.toString());
            req.setAttribute("errormessage", ecx.getMessage());
            req.getRequestDispatcher("/registration.jsp").forward(req, resp);
        }
    }
}
