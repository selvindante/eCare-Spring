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

/**
 * Created by Selvin
 * on 14.10.2014.
 */

@Controller
public class ClientServlet extends HttpServlet {

    @Autowired
    IClientService clientService;

    private static Logger logger = Logger.getLogger(ClientServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long clientId = Long.valueOf(req.getParameter("id"));
        String action = req.getParameter("action");
        Session session = Session.getInstance();
        session.setRole(req.getParameter("sessionRole"));
        session.setOn(Boolean.valueOf(req.getParameter("sessionStatus")));
        req.setAttribute("session", session);
        Client client = clientService.loadClient(clientId);
        req.setAttribute("client", client);
        switch(action) {
            case "viewClient":
                req.setAttribute("pagename", PageName.CLIENT.toString());
                logger.info("User " + session.getRole() + " went to client page.");
                req.getRequestDispatcher("/client.jsp").forward(req, resp);
                break;
            case "createContract":
                req.setAttribute("pagename", PageName.NEW_CONTRACT.toString());
                logger.info("User " + session.getRole() + " went to create contract page.");
                req.getRequestDispatcher("/createContract.jsp").forward(req, resp);
                break;
            case "editClient":
                req.setAttribute("pagename", PageName.EDIT_CLIENT.toString());
                logger.info("User " + session.getRole() + " went to edit client page.");
                req.getRequestDispatcher("/editClient.jsp").forward(req, resp);
                break;
            case "updateClient":
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
                    req.getRequestDispatcher("/client.jsp").forward(req, resp);
                } catch (ECareException ecx) {
                    req.setAttribute("pagename", PageName.EDIT_CLIENT.toString());
                    req.setAttribute("errormessage", ecx.getMessage());
                    req.getRequestDispatcher("/editClient.jsp").forward(req, resp);
                }
                break;
            case "addAmount":
                try {
                    int amount = Util.checkInt(req.getParameter("amount"));
                    client.addAmount(amount);
                    client = clientService.saveOrUpdateClient(client);
                    req.setAttribute("client", client);
                    req.setAttribute("pagename", PageName.CLIENT.toString());
                    req.setAttribute("successmessage", "Amount " + amount + " added to balance of client " + client.getFullName() + ".");
                    logger.info("User " + session.getRole() + " added amount to balance of client " + client + ".");
                    req.getRequestDispatcher("/client.jsp").forward(req, resp);
                } catch (ECareException ecx) {
                    req.setAttribute("pagename", PageName.CLIENT.toString());
                    req.setAttribute("errormessage", ecx.getMessage());
                    req.getRequestDispatcher("/client.jsp").forward(req, resp);
                }
            default:
                break;
        }
    }
}
