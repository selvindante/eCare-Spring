package ru.tsystems.tsproject.ecare.servlets;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.tsystems.tsproject.ecare.ECareException;
import ru.tsystems.tsproject.ecare.Session;
import ru.tsystems.tsproject.ecare.entities.Option;
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
import java.util.Arrays;
import java.util.List;

/**
 * Created by Selvin
 * on 14.10.2014.
 */

@Controller
public class OptionServlet extends HttpServlet {

    @Autowired
    IOptionService optionService;

    @Autowired
    ITariffService tariffService;

    private static Logger logger = Logger.getLogger(OptionServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long optionId = 0;
        long tariffId = 0;
        String action = req.getParameter("action");
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
        switch (action) {
            case "createOption":
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
                    req.getRequestDispatcher("/option.jsp").forward(req, resp);
                    break;
                } catch (ECareException ecx) {
                    tariff = tariffService.loadTariff(Long.valueOf(req.getParameter("tariffId")));
                    req.setAttribute("tariff", tariff);
                    req.setAttribute("errormessage", ecx.getMessage());
                    req.getRequestDispatcher("/createOption.jsp").forward(req, resp);
                }
            case "viewOption":
                option = optionService.loadOption(Long.valueOf(req.getParameter("id")));
                req.setAttribute("option", option);
                tariff = tariffService.loadTariff(Long.valueOf(req.getParameter("tariffId")));
                req.setAttribute("tariff", tariff);
                req.setAttribute("pagename", PageName.OPTION.toString());
                logger.info("User " + session.getRole() + " went to view option page.");
                req.getRequestDispatcher("/option.jsp").forward(req, resp);
                break;
            case "editOption":
                option = optionService.loadOption(Long.valueOf(req.getParameter("id")));
                req.setAttribute("option", option);
                tariff = tariffService.loadTariff(Long.valueOf(req.getParameter("tariffId")));
                req.setAttribute("tariff", tariff);
                req.setAttribute("pagename", PageName.OPTION_SETTINGS.toString());
                logger.info("User " + session.getRole() + " went to edit option page.");
                req.getRequestDispatcher("/editOption.jsp").forward(req, resp);
                break;
            case "updateOption":
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

                    tariffId = Long.valueOf(req.getParameter("tariffId"));
                    tariff = tariffService.loadTariff(tariffId);
                    req.setAttribute("tariff", tariff);
                    req.setAttribute("pagename", PageName.OPTION.toString());
                    req.setAttribute("successmessage", "Settings for option " + option.getTitle() + " updated.");
                    logger.info("Option " + option + " has been updated.");
                    req.getRequestDispatcher("/option.jsp").forward(req, resp);
                } catch (ECareException ecx) {
                    option = optionService.loadOption(Long.valueOf(req.getParameter("id")));
                    req.setAttribute("option", option);
                    tariff = tariffService.loadTariff(Long.valueOf(req.getParameter("tariffId")));
                    req.setAttribute("tariff", tariff);
                    req.setAttribute("pagename", PageName.OPTION_SETTINGS.toString());
                    req.setAttribute("errormessage", ecx.getMessage());
                    req.getRequestDispatcher("/editOption.jsp").forward(req, resp);
                }
                break;
            case "deleteOption":
                tariff = tariffService.loadTariff(Long.valueOf(req.getParameter("tariffId")));
                tariff.getOptions().remove(optionService.loadOption(Long.valueOf(req.getParameter("id"))));
                tariff = tariffService.saveOrUpdateTariff(tariff);
                optionId = Long.valueOf(req.getParameter("id"));
                optionService.deleteOption(optionId);
                logger.info("Option with id: " + optionId + " has been deleted from database.");
                req.setAttribute("tariff", tariff);
                req.setAttribute("pagename", PageName.TARIFF.toString());
                logger.info("User " + session.getRole() + " went to view tariff page.");
                req.getRequestDispatcher("/tariff.jsp").forward(req, resp);
                break;
            case "removeDependentOption":
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
                    req.getRequestDispatcher("/option.jsp").forward(req, resp);
                } catch(ECareException ecx) {
                    option = optionService.loadOption(Long.valueOf(req.getParameter("id")));
                    req.setAttribute("option", option);
                    tariff = tariffService.loadTariff(Long.valueOf(req.getParameter("tariffId")));
                    req.setAttribute("tariff", tariff);
                    req.setAttribute("pagename", PageName.OPTION_SETTINGS.toString());
                    req.setAttribute("errormessage", ecx.getMessage());
                    req.getRequestDispatcher("/editOption.jsp").forward(req, resp);
                }
                break;
            case "removeAllDependentOptions":
                try{
                    option = optionService.loadOption(Long.valueOf(req.getParameter("id")));
                    optionService.clearDependentOptions(option);
                    option = optionService.saveOrUpdateOption(option);
                    req.setAttribute("option", option);
                    tariff = tariffService.loadTariff(Long.valueOf(req.getParameter("tariffId")));
                    req.setAttribute("tariff", tariff);
                    req.setAttribute("pagename", PageName.OPTION.toString());
                    req.setAttribute("successmessage", "All dependent options removed from option " + option.getTitle() + ".");
                    req.getRequestDispatcher("/option.jsp").forward(req, resp);
                } catch(ECareException ecx) {
                    option = optionService.loadOption(Long.valueOf(req.getParameter("id")));
                    req.setAttribute("option", option);
                    tariff = tariffService.loadTariff(Long.valueOf(req.getParameter("tariffId")));
                    req.setAttribute("tariff", tariff);
                    req.setAttribute("pagename", PageName.OPTION_SETTINGS.toString());
                    req.setAttribute("errormessage", ecx.getMessage());
                    req.getRequestDispatcher("/editOption.jsp").forward(req, resp);
                }
                break;
            case "removeIncompatibleOption":
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
                    req.getRequestDispatcher("/option.jsp").forward(req, resp);
                } catch(ECareException ecx) {
                    option = optionService.loadOption(Long.valueOf(req.getParameter("id")));
                    req.setAttribute("option", option);
                    tariff = tariffService.loadTariff(Long.valueOf(req.getParameter("tariffId")));
                    req.setAttribute("tariff", tariff);
                    req.setAttribute("pagename", PageName.OPTION_SETTINGS.toString());
                    req.setAttribute("errormessage", ecx.getMessage());
                    req.getRequestDispatcher("/editOption.jsp").forward(req, resp);
                }
                break;
            case "removeAllIncompatibleOptions":
                try{
                    option = optionService.loadOption(Long.valueOf(req.getParameter("id")));
                    optionService.clearIncompatibleOptions(option);
                    option = optionService.saveOrUpdateOption(option);
                    req.setAttribute("option", option);
                    tariff = tariffService.loadTariff(Long.valueOf(req.getParameter("tariffId")));
                    req.setAttribute("tariff", tariff);
                    req.setAttribute("pagename", PageName.OPTION.toString());
                    req.setAttribute("successmessage", "All incompatible options removed from option " + option.getTitle() + ".");
                    req.getRequestDispatcher("/option.jsp").forward(req, resp);
                } catch(ECareException ecx) {
                    option = optionService.loadOption(Long.valueOf(req.getParameter("id")));
                    req.setAttribute("option", option);
                    tariff = tariffService.loadTariff(Long.valueOf(req.getParameter("tariffId")));
                    req.setAttribute("tariff", tariff);
                    req.setAttribute("pagename", PageName.OPTION_SETTINGS.toString());
                    req.setAttribute("errormessage", ecx.getMessage());
                    req.getRequestDispatcher("/editOption.jsp").forward(req, resp);
                }
                break;
            default: break;
        }
    }
}
