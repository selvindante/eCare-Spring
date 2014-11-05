package ru.tsystems.tsproject.ecare.servlets;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.tsystems.tsproject.ecare.ECareException;
import ru.tsystems.tsproject.ecare.Session;
import ru.tsystems.tsproject.ecare.entities.Tariff;
import ru.tsystems.tsproject.ecare.service.IOptionService;
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
public class TariffServlet extends HttpServlet {

    @Autowired
    ITariffService tariffService;

    @Autowired
    IOptionService optionService;

    private static Logger logger = Logger.getLogger(TariffServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long tariffId = 0;
        String action = req.getParameter("action");
        Session session = Session.getInstance();
        session.setRole(req.getParameter("sessionRole"));
        session.setOn(Boolean.valueOf(req.getParameter("sessionStatus")));
        req.setAttribute("session", session);
        Tariff tariff = null;
        switch (action) {
            case "createTariff":
                try {
                    String title = Util.checkStringLength(Util.checkStringOnEmpty(req.getParameter("title")));
                    int price = Util.checkInt(req.getParameter("price"));
                    tariff = new Tariff(title, price);
                    tariff = tariffService.saveOrUpdateTariff(tariff);
                    req.setAttribute("tariff", tariff);
                    req.setAttribute("pagename", PageName.TARIFF.toString());
                    req.setAttribute("successmessage", "Tariff " + tariff.getTitle() + " created and saved in database.");
                    logger.info("New tariff " + tariff + " created.");
                    req.getRequestDispatcher("/tariff.jsp").forward(req, resp);
                } catch (ECareException ecx) {
                    req.setAttribute("pagename", PageName.NEW_TARIFF.toString());
                    req.setAttribute("errormessage", ecx.getMessage());
                    req.getRequestDispatcher("/createTariff.jsp").forward(req, resp);
                }
                break;
            case "viewTariff":
                tariffId = Long.valueOf(req.getParameter("id"));
                tariff = tariffService.loadTariff(tariffId);
                req.setAttribute("tariff", tariff);
                req.setAttribute("pagename", PageName.TARIFF.toString());
                logger.info("User " + session.getRole() + " went to view tariff page.");
                req.getRequestDispatcher("/tariff.jsp").forward(req, resp);
                break;
            case "deleteTariff":
                tariffId = Long.valueOf(req.getParameter("id"));
                tariffService.deleteTariff(tariffId);
                logger.info("Tariff with id: " + tariffId + " deleted from database.");
                List<Tariff> tariffs = tariffService.getAllTariffs();
                req.setAttribute("tariffs", tariffs);
                req.setAttribute("pagename", PageName.TARIFFS.toString());
                logger.info("User " + session.getRole() + " went to all tariffs page.");
                req.getRequestDispatcher("/tariffsList.jsp").forward(req, resp);
                break;
            case "createOption":
                tariffId = Long.valueOf(req.getParameter("id"));
                tariff = tariffService.loadTariff(tariffId);
                req.setAttribute("tariff", tariff);
                req.setAttribute("pagename", PageName.NEW_OPTION.toString());
                logger.info("User " + session.getRole() + " went to create new option page.");
                req.getRequestDispatcher("/createOption.jsp").forward(req, resp);
                break;
            case "deleteAllOptions":
                tariffId = Long.valueOf(req.getParameter("id"));
                optionService.deleteAllOptionsForTariff(tariffId);
                logger.info("All options for tariff id: " + tariffId + " deleted from database.");
                tariff = tariffService.loadTariff(tariffId);
                tariff.setOptions(optionService.getAllOptionsForTariff(tariffId));
                req.setAttribute("tariff", tariff);
                req.setAttribute("pagename", PageName.TARIFF.toString());
                logger.info("User " + session.getRole() + " went to view tariff page.");
                req.getRequestDispatcher("/tariff.jsp").forward(req, resp);
                break;
            default: break;
        }
    }
}
