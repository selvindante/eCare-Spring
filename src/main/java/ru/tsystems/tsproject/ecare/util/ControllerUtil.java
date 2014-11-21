package ru.tsystems.tsproject.ecare.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tsystems.tsproject.ecare.Session;
import ru.tsystems.tsproject.ecare.service.ITariffService;

import javax.servlet.http.HttpServletRequest;

/**
 * This class contains static util methods for controller logic.
 *
 * @author Starostin Konstantin
 */

@Component
public class ControllerUtil {

    /*Tariff service instance*/
    @Autowired
    private static ITariffService tariffService;

    /**
     * Method checks session of user and set it as attribute of request.
     *
     * @param req input HTTP servlet request.
     */
    public static void setSession(HttpServletRequest req) {
        Session session = Session.getInstance();
        session.setRole(req.getParameter("sessionRole"));
        session.setOn(Boolean.valueOf(req.getParameter("sessionStatus")));
        req.setAttribute("session", session);
    }
}
