package ru.tsystems.tsproject.ecare.servlets;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.tsystems.tsproject.ecare.Session;
import ru.tsystems.tsproject.ecare.entities.Tariff;
import ru.tsystems.tsproject.ecare.service.ITariffService;
import ru.tsystems.tsproject.ecare.util.PageName;

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
public class TariffsListServlet extends HttpServlet {

    @Autowired
    ITariffService tariffService;

    private static Logger logger = Logger.getLogger(TariffsListServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Session session = Session.getInstance();
        session.setRole(req.getParameter("sessionRole"));
        session.setOn(Boolean.valueOf(req.getParameter("sessionStatus")));
        req.setAttribute("session", session);
        List<Tariff> tariffs = null;
        switch(action) {
            case "createTariff":
                req.setAttribute("pagename", PageName.NEW_TARIFF.toString());
                logger.info("User " + session.getRole() + " went to create new tariff page.");
                req.getRequestDispatcher("/createTariff.jsp").forward(req, resp);
                break;
            case "deleteAllTariffs":
                tariffService.deleteAllTariffs();
                logger.info("All tariffs deleted from database.");
                tariffs = tariffService.getAllTariffs();
                req.setAttribute("tariffs", tariffs);
                req.setAttribute("pagename", PageName.TARIFFS.toString());
                logger.info("User " + session.getRole() + " went to view all tariffs page.");
                req.getRequestDispatcher("/tariffsList.jsp").forward(req, resp);
                break;
            default: break;
        }
    }
}
