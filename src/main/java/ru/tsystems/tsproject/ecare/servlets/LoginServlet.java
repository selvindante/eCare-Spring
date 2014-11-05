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
import java.util.List;

/**
 * Created by Selvin
 * on 12.10.2014.
 */

@Controller
public class LoginServlet extends HttpServlet {

    @Autowired
    private IClientService clientService;

    private static Logger logger = Logger.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Session session = Session.getInstance();
        switch (action) {
            case "login":
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
                        req.getRequestDispatcher("/client.jsp").forward(req, resp);
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
                        req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
                    }
                } catch (ECareException ecx) {
                    req.setAttribute("errormessage", ecx.getMessage());
                    req.getRequestDispatcher("/login.jsp").forward(req, resp);
                }
                break;
            case "registration":
                req.setAttribute("name", "");
                req.setAttribute("lastname", "");
                req.setAttribute("birthdate", "");
                req.setAttribute("passport", "");
                req.setAttribute("address", "");
                req.setAttribute("email", "");
                req.setAttribute("pagename", PageName.REGISTRATION.toString());
                logger.info("New user has enter to the registration page.");
                req.getRequestDispatcher("/registration.jsp").forward(req, resp);
                break;
            case "logout":
                session.setOn(false);
                req.setAttribute("session", session);
                logger.info("User has logout from application.");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
                break;
            default: break;
        }
    }
}
