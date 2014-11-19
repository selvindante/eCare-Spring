package ru.tsystems.tsproject.ecare.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.tsystems.tsproject.ecare.ECareException;
import ru.tsystems.tsproject.ecare.Session;
import ru.tsystems.tsproject.ecare.entities.Option;
import ru.tsystems.tsproject.ecare.entities.Tariff;
import ru.tsystems.tsproject.ecare.service.IOptionService;
import ru.tsystems.tsproject.ecare.service.ITariffService;
import ru.tsystems.tsproject.ecare.util.PageName;
import ru.tsystems.tsproject.ecare.util.Util;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Selvin
 * on 15.11.2014.
 */

@Controller
public class OptionController {
    @Autowired
    IOptionService optionService;
    @Autowired
    ITariffService tariffService;
    private static Logger logger = Logger.getLogger(OptionController.class);

    @RequestMapping(value = "/createOption", method = RequestMethod.POST)
    public String createOption(HttpServletRequest req) {
        long tariffId = 0;
        Session session = Session.getInstance();
        session.setRole(req.getParameter("sessionRole"));
        session.setOn(Boolean.valueOf(req.getParameter("sessionStatus")));
        req.setAttribute("session", session);
        Option option = null;
        long dependentOptionId = 0;
        Option dependentOption = null;
        long incompatibleOptionId = 0;
        Option incompatibleOption = null;
        Tariff tariff = null;
        try {
            tariffId = Long.valueOf(req.getParameter("tariffId"));
            String title = Util.checkStringLength(Util.checkStringOnEmpty(req.getParameter("title")));
            int price = Util.checkInt(req.getParameter("price"));
            int costOfConnection = Util.checkInt(req.getParameter("costOfConnection"));

            tariff = tariffService.loadTariff(tariffId);
            option = new Option(tariff, title, price, costOfConnection);

            String dependentOptionsArray[] = req.getParameterValues("dependentOptions");
            if(dependentOptionsArray != null) {
                List<String> dependentOptionsId = Arrays.asList(dependentOptionsArray);
                for(String stringId: dependentOptionsId) {
                    dependentOptionId = Long.valueOf(stringId);
                    dependentOption = optionService.loadOption(dependentOptionId);
                    optionService.setDependentOption(option, dependentOption);
                    optionService.setDependentOption(dependentOption, option);
                }
            }

            String incompatibleOptionsArray[] = req.getParameterValues("incompatibleOptions");
            if(incompatibleOptionsArray != null) {
                List<String> incompatibleOptionsId = Arrays.asList(incompatibleOptionsArray);
                for (String stringId : incompatibleOptionsId) {
                    incompatibleOptionId = Long.valueOf(stringId);
                    incompatibleOption = optionService.loadOption(incompatibleOptionId);
                    optionService.setIncompatibleOption(option, incompatibleOption);
                    optionService.setIncompatibleOption(incompatibleOption, option);
                }
            }

            option = optionService.saveOrUpdateOption(option);
            req.setAttribute("option", option);
            tariff.addOption(option);
            tariff = tariffService.saveOrUpdateTariff(tariff);
            req.setAttribute("tariff", tariff);
            req.setAttribute("pagename", PageName.OPTION.toString());
            logger.info("New option " + option + " has created.");
            req.setAttribute("successmessage", "Option " + option.getTitle() + " created and saved in database.");
            return "operator/option";
        } catch (ECareException ecx) {
            tariff = tariffService.loadTariff(Long.valueOf(req.getParameter("tariffId")));
            req.setAttribute("tariff", tariff);
            req.setAttribute("pagename", PageName.NEW_OPTION.toString());
            req.setAttribute("errormessage", ecx.getMessage());
            return "operator/newOption";
        }
    }

    @RequestMapping(value = "/viewOption", method = RequestMethod.POST)
    public String viewOption(HttpServletRequest req) {
        Session session = Session.getInstance();
        session.setRole(req.getParameter("sessionRole"));
        session.setOn(Boolean.valueOf(req.getParameter("sessionStatus")));
        req.setAttribute("session", session);
        Option option = optionService.loadOption(Long.valueOf(req.getParameter("id")));
        req.setAttribute("option", option);
        Tariff tariff = tariffService.loadTariff(Long.valueOf(req.getParameter("tariffId")));
        req.setAttribute("tariff", tariff);
        req.setAttribute("pagename", PageName.OPTION.toString());
        logger.info("User " + session.getRole() + " went to view option page.");
        return "operator/option";
    }

    @RequestMapping(value = "/editOption", method = RequestMethod.POST)
    public String editOption(HttpServletRequest req) {
        Session session = Session.getInstance();
        session.setRole(req.getParameter("sessionRole"));
        session.setOn(Boolean.valueOf(req.getParameter("sessionStatus")));
        req.setAttribute("session", session);
        Option option = optionService.loadOption(Long.valueOf(req.getParameter("id")));
        req.setAttribute("option", option);
        Tariff tariff = tariffService.loadTariff(Long.valueOf(req.getParameter("tariffId")));
        req.setAttribute("tariff", tariff);
        req.setAttribute("pagename", PageName.OPTION_SETTINGS.toString());
        logger.info("User " + session.getRole() + " went to edit option page.");
        return "operator/editOption";
    }

    @RequestMapping(value = "/updateOption", method = RequestMethod.POST)
    public String updateOption(HttpServletRequest req) {
        Session session = Session.getInstance();
        session.setRole(req.getParameter("sessionRole"));
        session.setOn(Boolean.valueOf(req.getParameter("sessionStatus")));
        req.setAttribute("session", session);
        Option option = null;
        long dependentOptionId = 0;
        Option dependentOption = null;
        long incompatibleOptionId = 0;
        Option incompatibleOption = null;
        Tariff tariff = null;
        try {
            option = optionService.loadOption(Long.valueOf(req.getParameter("id")));

            String dependentOptionsArray1[] = req.getParameterValues("dependentOptions");
            if(dependentOptionsArray1 != null) {
                List<String> dependentOptionsId = Arrays.asList(dependentOptionsArray1);

                for(String stringId: dependentOptionsId) {
                    dependentOptionId = Long.valueOf(stringId);
                    dependentOption = optionService.loadOption(dependentOptionId);
                    optionService.setDependentOption(option, dependentOption);
                    optionService.setDependentOption(dependentOption, option);
                }
            }

            String incompatibleOptionsArray1[] = req.getParameterValues("incompatibleOptions");
            if(incompatibleOptionsArray1 != null) {
                List<String> incompatibleOptionsId = Arrays.asList(incompatibleOptionsArray1);
                for (String stringId : incompatibleOptionsId) {
                    incompatibleOptionId = Long.valueOf(stringId);
                    incompatibleOption = optionService.loadOption(incompatibleOptionId);
                    optionService.setIncompatibleOption(option, incompatibleOption);
                    optionService.setIncompatibleOption(incompatibleOption, option);
                }
            }

            option = optionService.saveOrUpdateOption(option);
            req.setAttribute("option", option);

            long tariffId = Long.valueOf(req.getParameter("tariffId"));
            tariff = tariffService.loadTariff(tariffId);
            req.setAttribute("tariff", tariff);
            req.setAttribute("pagename", PageName.OPTION.toString());
            req.setAttribute("successmessage", "Settings for option " + option.getTitle() + " updated.");
            logger.info("Option " + option + " has been updated.");
            return "operator/option";
        } catch (ECareException ecx) {
            option = optionService.loadOption(Long.valueOf(req.getParameter("id")));
            req.setAttribute("option", option);
            tariff = tariffService.loadTariff(Long.valueOf(req.getParameter("tariffId")));
            req.setAttribute("tariff", tariff);
            req.setAttribute("pagename", PageName.OPTION_SETTINGS.toString());
            req.setAttribute("errormessage", ecx.getMessage());
            return "operator/editOption";
        }
    }

    @RequestMapping(value = "/deleteOption", method = RequestMethod.POST)
    public String deleteOption(HttpServletRequest req) {
        Session session = Session.getInstance();
        session.setRole(req.getParameter("sessionRole"));
        session.setOn(Boolean.valueOf(req.getParameter("sessionStatus")));
        req.setAttribute("session", session);
        Tariff tariff = tariffService.loadTariff(Long.valueOf(req.getParameter("tariffId")));
        tariff.getOptions().remove(optionService.loadOption(Long.valueOf(req.getParameter("id"))));
        tariff = tariffService.saveOrUpdateTariff(tariff);
        long optionId = Long.valueOf(req.getParameter("id"));
        //optionService.deleteOption(optionId);
        logger.info("Option with id: " + optionId + " has been deleted from database.");
        req.setAttribute("tariff", tariff);
        req.setAttribute("pagename", PageName.TARIFF.toString());
        logger.info("User " + session.getRole() + " went to view tariff page.");
        return "operator/tariff";
    }

    @RequestMapping(value = "/removeDependentOption", method = RequestMethod.POST)
    public String removeDependentOption(HttpServletRequest req) {
        Session session = Session.getInstance();
        session.setRole(req.getParameter("sessionRole"));
        session.setOn(Boolean.valueOf(req.getParameter("sessionStatus")));
        req.setAttribute("session", session);
        Option option = null;
        Option dependentOption = null;
        Tariff tariff = null;
        try {
            option = optionService.loadOption(Long.valueOf(req.getParameter("id")));
            dependentOption = optionService.loadOption(Long.valueOf(req.getParameter("dependentOptionId")));
            option = optionService.deleteDependentOption(option, dependentOption);
            optionService.deleteDependentOption(dependentOption, option);
            req.setAttribute("option", option);
            tariff = tariffService.loadTariff(Long.valueOf(req.getParameter("tariffId")));
            req.setAttribute("tariff", tariff);
            req.setAttribute("pagename", PageName.OPTION.toString());
            req.setAttribute("successmessage", "Option " + dependentOption.getTitle() + " is no longer dependent to option " + option.getTitle() + ".");
            return "operator/option";
        } catch(ECareException ecx) {
            option = optionService.loadOption(Long.valueOf(req.getParameter("id")));
            req.setAttribute("option", option);
            tariff = tariffService.loadTariff(Long.valueOf(req.getParameter("tariffId")));
            req.setAttribute("tariff", tariff);
            req.setAttribute("pagename", PageName.OPTION_SETTINGS.toString());
            req.setAttribute("errormessage", ecx.getMessage());
            return "operator/editOption.jsp";
        }
    }

    @RequestMapping(value = "/removeAllDependentOptions", method = RequestMethod.POST)
    public String removeAllDependentOptions(HttpServletRequest req) {
        Session session = Session.getInstance();
        session.setRole(req.getParameter("sessionRole"));
        session.setOn(Boolean.valueOf(req.getParameter("sessionStatus")));
        req.setAttribute("session", session);
        Option option = null;
        Tariff tariff = null;
        try{
            option = optionService.loadOption(Long.valueOf(req.getParameter("id")));
            optionService.clearDependentOptions(option);
            option = optionService.saveOrUpdateOption(option);
            req.setAttribute("option", option);
            tariff = tariffService.loadTariff(Long.valueOf(req.getParameter("tariffId")));
            req.setAttribute("tariff", tariff);
            req.setAttribute("pagename", PageName.OPTION.toString());
            req.setAttribute("successmessage", "All dependent options removed from option " + option.getTitle() + ".");
            return "operator/option";
        } catch(ECareException ecx) {
            option = optionService.loadOption(Long.valueOf(req.getParameter("id")));
            req.setAttribute("option", option);
            tariff = tariffService.loadTariff(Long.valueOf(req.getParameter("tariffId")));
            req.setAttribute("tariff", tariff);
            req.setAttribute("pagename", PageName.OPTION_SETTINGS.toString());
            req.setAttribute("errormessage", ecx.getMessage());
            return "operator/editOption.jsp";
        }
    }

    @RequestMapping(value = "/removeIncompatibleOption", method = RequestMethod.POST)
    public String removeIncompatibleOption(HttpServletRequest req) {
        Session session = Session.getInstance();
        session.setRole(req.getParameter("sessionRole"));
        session.setOn(Boolean.valueOf(req.getParameter("sessionStatus")));
        req.setAttribute("session", session);
        Option option = null;
        Option incompatibleOption = null;
        Tariff tariff = null;
        try{
            option = optionService.loadOption(Long.valueOf(req.getParameter("id")));
            incompatibleOption = optionService.loadOption(Long.valueOf(req.getParameter("incompatibleOptionId")));
            option = optionService.deleteIncompatibleOption(option, incompatibleOption);
            optionService.deleteIncompatibleOption(incompatibleOption, option);
            req.setAttribute("option", option);
            tariff = tariffService.loadTariff(Long.valueOf(req.getParameter("tariffId")));
            req.setAttribute("tariff", tariff);
            req.setAttribute("pagename", PageName.OPTION.toString());
            req.setAttribute("successmessage", "Option " + incompatibleOption.getTitle() + " is no longer incompatible with option " + option.getTitle() + ".");
            return "operator/option";
        } catch(ECareException ecx) {
            option = optionService.loadOption(Long.valueOf(req.getParameter("id")));
            req.setAttribute("option", option);
            tariff = tariffService.loadTariff(Long.valueOf(req.getParameter("tariffId")));
            req.setAttribute("tariff", tariff);
            req.setAttribute("pagename", PageName.OPTION_SETTINGS.toString());
            req.setAttribute("errormessage", ecx.getMessage());
            return "operator/editOption";
        }
    }

    @RequestMapping(value = "/removeAllIncompatibleOptions", method = RequestMethod.POST)
    public String removeAllIncompatibleOptions(HttpServletRequest req) {
        Session session = Session.getInstance();
        session.setRole(req.getParameter("sessionRole"));
        session.setOn(Boolean.valueOf(req.getParameter("sessionStatus")));
        req.setAttribute("session", session);
        Option option = null;
        Tariff tariff = null;
        try{
            option = optionService.loadOption(Long.valueOf(req.getParameter("id")));
            optionService.clearIncompatibleOptions(option);
            option = optionService.saveOrUpdateOption(option);
            req.setAttribute("option", option);
            tariff = tariffService.loadTariff(Long.valueOf(req.getParameter("tariffId")));
            req.setAttribute("tariff", tariff);
            req.setAttribute("pagename", PageName.OPTION.toString());
            req.setAttribute("successmessage", "All incompatible options removed from option " + option.getTitle() + ".");
            return "operator/option";
        } catch(ECareException ecx) {
            option = optionService.loadOption(Long.valueOf(req.getParameter("id")));
            req.setAttribute("option", option);
            tariff = tariffService.loadTariff(Long.valueOf(req.getParameter("tariffId")));
            req.setAttribute("tariff", tariff);
            req.setAttribute("pagename", PageName.OPTION_SETTINGS.toString());
            req.setAttribute("errormessage", ecx.getMessage());
            return "operator/editOption";
        }
    }
}
