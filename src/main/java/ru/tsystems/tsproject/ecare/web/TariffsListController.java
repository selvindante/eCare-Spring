package ru.tsystems.tsproject.ecare.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.tsystems.tsproject.ecare.Session;
import ru.tsystems.tsproject.ecare.entities.Tariff;
import ru.tsystems.tsproject.ecare.service.ITariffService;
import ru.tsystems.tsproject.ecare.util.PageName;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Selvin
 * on 15.11.2014.
 */

@Controller
public class TariffsListController {
    @Autowired
    ITariffService tariffService;
    private static Logger logger = Logger.getLogger(TariffsListController.class);

    @RequestMapping(value = "/viewAllTariffs", method = RequestMethod.POST)
    public String viewAllTariffs(HttpServletRequest req) {
        Session session = Session.getInstance();
        session.setRole(req.getParameter("sessionRole"));
        session.setOn(Boolean.valueOf(req.getParameter("sessionStatus")));
        req.setAttribute("session", session);
        List<Tariff> tariffs = tariffService.getAllTariffs();
        req.setAttribute("tariffs", tariffs);
        req.setAttribute("pagename", PageName.TARIFFS.toString());
        logger.info("User " + session.getRole() + " went to view all tariffs page.");
        return "operator/tariffsList";
    }

    @RequestMapping(value = "/newTariff", method = RequestMethod.POST)
    public String newTariff(HttpServletRequest req) {
        Session session = Session.getInstance();
        session.setRole(req.getParameter("sessionRole"));
        session.setOn(Boolean.valueOf(req.getParameter("sessionStatus")));
        req.setAttribute("session", session);
        req.setAttribute("pagename", PageName.NEW_TARIFF.toString());
        logger.info("User " + session.getRole() + " went to create new tariff page.");
        return "operator/createTariff";
    }
}
