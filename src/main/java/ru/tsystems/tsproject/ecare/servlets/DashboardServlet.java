package ru.tsystems.tsproject.ecare.servlets;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.tsystems.tsproject.ecare.ECareException;
import ru.tsystems.tsproject.ecare.Session;
import ru.tsystems.tsproject.ecare.entities.Client;
import ru.tsystems.tsproject.ecare.entities.Tariff;
import ru.tsystems.tsproject.ecare.service.IClientService;
import ru.tsystems.tsproject.ecare.service.ITariffService;
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
 * on 14.10.2014.
 */

@Controller
public class DashboardServlet extends HttpServlet {

    @Autowired
    IClientService clientService;

    @Autowired
    ITariffService tariffService;

    private static Logger logger = Logger.getLogger(DashboardServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Session session = Session.getInstance();
        session.setRole(req.getParameter("sessionRole"));
        session.setOn(Boolean.valueOf(req.getParameter("sessionStatus")));
        req.setAttribute("session", session);
        List<Client> clientsList = null;
        switch(action) {
            case "viewDashboard":
                clientsList = clientService.getAllClients();
                req.setAttribute("clientsList", clientsList);
                req.setAttribute("pagename", PageName.DASHBOARD.toString());
                logger.info("User " + session.getRole() + " went to view dashboard page.");
                req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
                break;
            case "searchClientByNumber":
                try {
                    long number = Util.checkLong(req.getParameter("number"));
                    logger.info("User " + session.getRole() + " searching of client by number " + number + ".");
                    Client client = clientService.findClientByNumber(number);
                    req.setAttribute("client", client);
                    req.setAttribute("pagename", PageName.CLIENT.toString());
                    req.setAttribute("successmessage", "Client " + client.getName() + " found and loaded from database.");
                    logger.info("User " + session.getRole() + " went to client page.");
                    req.getRequestDispatcher("/client.jsp").forward(req, resp);
                } catch (ECareException ecx) {
                    clientsList = clientService.getAllClients();
                    req.setAttribute("clientsList", clientsList);
                    req.setAttribute("pagename", PageName.DASHBOARD.toString());
                    req.setAttribute("errormessage", ecx.getMessage());
                    req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
                }
                break;
            case "viewAllTariffs":
                List<Tariff> tariffs = tariffService.getAllTariffs();
                req.setAttribute("tariffs", tariffs);
                req.setAttribute("pagename", PageName.TARIFFS.toString());
                logger.info("User " + session.getRole() + " went to view all tariffs page.");
                req.getRequestDispatcher("/tariffsList.jsp").forward(req, resp);
                break;
            case "deleteClient":
                long clientId = Long.valueOf(req.getParameter("id"));
                clientService.deleteClient(clientId);
                logger.info("User " + session.getRole() + " deleted are client with id:" + clientId + " from database.");
                clientsList = clientService.getAllClients();
                req.setAttribute("clientsList", clientsList);
                req.setAttribute("pagename", PageName.DASHBOARD.toString());
                logger.info("User " + session.getRole() + " went to view dashboard page.");
                req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
                break;
            case "deleteAllClients":
                clientService.deleteAllClients();
                logger.info("User " + session.getRole() + " deleted all clients from database.");
                clientsList = clientService.getAllClients();
                req.setAttribute("clientsList", clientsList);
                req.setAttribute("pagename", PageName.DASHBOARD.toString());
                logger.info("User " + session.getRole() + " went to view dashboard page.");
                req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
                break;
            default: break;
        }
    }
}
