package ru.tsystems.tsproject.ecare.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.tsystems.tsproject.ecare.ECareException;
import ru.tsystems.tsproject.ecare.entities.Tariff;
import ru.tsystems.tsproject.ecare.service.ITariffService;
import ru.tsystems.tsproject.ecare.util.ControllerUtil;
import ru.tsystems.tsproject.ecare.util.PageName;
import ru.tsystems.tsproject.ecare.util.Util;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Controller of tariff.jsp and createTariff.jsp pages.
 *
 * @author Starostin Konstantin
 */
@Controller
public class TariffController {
    @Autowired
    ITariffService tariffService;
    private static Logger logger = Logger.getLogger(TariffController.class);

    @RequestMapping(value = "/createTariff", method = RequestMethod.POST)
    public String createTariff(HttpServletRequest req) {
        ControllerUtil.setRole(req);
        Tariff tariff = null;
        try {
            String title = Util.checkStringLength(Util.checkStringOnEmpty(req.getParameter("title")));
            int price = Util.checkInt(req.getParameter("price"));
            tariff = new Tariff(title, price);
            tariff = tariffService.saveOrUpdateTariff(tariff);
            req.setAttribute("tariff", tariff);
            req.setAttribute("pagename", PageName.TARIFF.toString());
            req.setAttribute("successmessage", "Tariff " + tariff.getTitle() + " created and saved in database.");
            logger.info("New tariff " + tariff + " created.");
            return "operator/tariff";
        } catch (ECareException ecx) {
            req.setAttribute("pagename", PageName.NEW_TARIFF.toString());
            req.setAttribute("errormessage", ecx.getMessage());
            return "operator/createTariff";
        }
    }

    @RequestMapping(value = "/viewTariff", method = RequestMethod.POST)
    public String viewTariff(HttpServletRequest req) {
        ControllerUtil.setRole(req);
        long tariffId = Long.valueOf(req.getParameter("id"));
        Tariff tariff = tariffService.loadTariff(tariffId);
        req.setAttribute("tariff", tariff);
        req.setAttribute("pagename", PageName.TARIFF.toString());
        logger.info("User went to view tariff page.");
        return "operator/tariff";
    }

    @RequestMapping(value = "/deleteTariff", method = RequestMethod.POST)
    public String deleteTariff(HttpServletRequest req) {
        ControllerUtil.setRole(req);
        long tariffId = Long.valueOf(req.getParameter("id"));
        List<Tariff> tariffs = null;
        try {
            tariffService.deleteTariff(tariffId);
            logger.info("Tariff with id: " + tariffId + " deleted from database.");
            tariffs = tariffService.getAllTariffs();
            req.setAttribute("tariffs", tariffs);
            req.setAttribute("pagename", PageName.TARIFFS.toString());
            req.setAttribute("successmessage", "Tariff with id: " + tariffId + " deleted from database.");
            logger.info("User went to all tariffs page.");
            return "operator/tariffsList";
        } catch (ECareException ecx) {
            tariffs = tariffService.getAllTariffs();
            req.setAttribute("tariffs", tariffs);
            req.setAttribute("pagename", PageName.TARIFFS.toString());
            req.setAttribute("errormessage", ecx.getMessage());
            return "operator/tariffsList";
        }
    }

    @RequestMapping(value = "/newOption", method = RequestMethod.POST)
    public String newOption(HttpServletRequest req) {
        ControllerUtil.setRole(req);
        long tariffId = Long.valueOf(req.getParameter("id"));
        Tariff tariff = tariffService.loadTariff(tariffId);
        req.setAttribute("tariff", tariff);
        req.setAttribute("pagename", PageName.NEW_OPTION.toString());
        logger.info("User went to create new option page.");
        return "operator/createOption";
    }
}
