package ru.tsystems.tsproject.ecare.servlets;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.tsystems.tsproject.ecare.ECareException;
import ru.tsystems.tsproject.ecare.Session;
import ru.tsystems.tsproject.ecare.entities.Client;
import ru.tsystems.tsproject.ecare.entities.Contract;
import ru.tsystems.tsproject.ecare.service.IClientService;
import ru.tsystems.tsproject.ecare.service.IContractService;
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
public class ContractCreationServlet extends HttpServlet {

    @Autowired
    IClientService clientService;

    @Autowired
    IContractService contractService;

    private static Logger logger = Logger.getLogger(ContractCreationServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long clientId = Long.valueOf(req.getParameter("id"));
        Session session = Session.getInstance();
        session.setRole(req.getParameter("sessionRole"));
        session.setOn(Boolean.valueOf(req.getParameter("sessionStatus")));
        req.setAttribute("session", session);
        Client client = clientService.loadClient(clientId);
        req.setAttribute("client", client);
        try {
            logger.info("Creating of new contract with number: " + req.getParameter("number") + ".");
            long number = Util.checkNumberOnExisting(req.getParameter("number"));
            Contract contract = new Contract(client, number, null, false, false);
            client.addContract(contract);
            client = clientService.saveOrUpdateClient(client);
            req.setAttribute("client", client);
            req.setAttribute("pagename", PageName.CLIENT.toString());
            req.setAttribute("successmessage", "Contract " + contract.getNumber() + " created for client " + client.getFullName() + ".");
            logger.info("New contract: " + contract + " has created.");
            req.getRequestDispatcher("/client.jsp").forward(req, resp);
        } catch (ECareException ecx) {
            req.setAttribute("pagename", PageName.NEW_CONTRACT.toString());
            req.setAttribute("errormessage", ecx.getMessage());
            req.getRequestDispatcher("/createContract.jsp").forward(req, resp);
        }

    }
}
