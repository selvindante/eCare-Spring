package ru.tsystems.tsproject.ecare.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.tsystems.tsproject.ecare.ECareException;
import ru.tsystems.tsproject.ecare.Session;
import ru.tsystems.tsproject.ecare.entities.Client;
import ru.tsystems.tsproject.ecare.entities.Contract;
import ru.tsystems.tsproject.ecare.entities.Option;
import ru.tsystems.tsproject.ecare.entities.Tariff;
import ru.tsystems.tsproject.ecare.service.IClientService;
import ru.tsystems.tsproject.ecare.service.IContractService;
import ru.tsystems.tsproject.ecare.service.IOptionService;
import ru.tsystems.tsproject.ecare.service.ITariffService;
import ru.tsystems.tsproject.ecare.util.ControllerUtil;
import ru.tsystems.tsproject.ecare.util.PageName;
import ru.tsystems.tsproject.ecare.util.Util;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Selvin
 * on 15.11.2014.
 */

@Controller
public class ContractController {
    @Autowired
    IClientService clientService;
    @Autowired
    IContractService contractService;
    @Autowired
    ITariffService tariffService;
    @Autowired
    IOptionService optionService;
    private static Logger logger = Logger.getLogger(ContractController.class);

    @RequestMapping(value = "/createContract", method = RequestMethod.POST)
    public String createContract(HttpServletRequest req) {
        long clientId = Long.valueOf(req.getParameter("id"));
        ControllerUtil.setSession(req);
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
            return "client/client";
        } catch (ECareException ecx) {
            req.setAttribute("client", client);
            req.setAttribute("pagename", PageName.NEW_CONTRACT.toString());
            req.setAttribute("errormessage", ecx.getMessage());
            return "client/createContract";
        }
    }

    @RequestMapping(value = "/viewContract", method = RequestMethod.POST)
    public String viewContract(HttpServletRequest req) {
        long contractId = Long.valueOf(req.getParameter("id"));
        ControllerUtil.setSession(req);
        Contract contract = contractService.loadContract(contractId);
        req.setAttribute("contract", contract);
        req.setAttribute("client", contract.getClient());
        req.setAttribute("pagename", PageName.CONTRACT.toString());
        logger.info("User " + Session.getInstance().getRole() + " went to view contract page.");
        return "client/contract";
    }

    @RequestMapping(value = "/deleteContract", method = RequestMethod.POST)
    public String deleteContract(HttpServletRequest req) {
        long contractId = Long.valueOf(req.getParameter("id"));
        ControllerUtil.setSession(req);
        try{
            Contract contract = contractService.loadContract(contractId);
            Client client = contract.getClient();
            client.getContracts().remove(contract);
            client = clientService.saveOrUpdateClient(client);
            req.setAttribute("client", client);
            req.setAttribute("pagename", PageName.CLIENT.toString());
            req.setAttribute("successmessage", "Contract " + contract.getNumber() + " deleted from database.");
            logger.info("User " + Session.getInstance().getRole() + " deleted contract with id: " + contractId + " from database.");
            return "client/client";
        }catch (ECareException ecx){
            req.setAttribute("client", contractService.loadContract(contractId).getClient());
            req.setAttribute("pagename", PageName.CLIENT.toString());
            req.setAttribute("errormessage", ecx.getMessage());
            return "client/client";
        }
    }

    @RequestMapping(value = "/blockByOperator", method = RequestMethod.POST)
    public String blockByOperator(HttpServletRequest req) {
        long contractId = Long.valueOf(req.getParameter("id"));
        ControllerUtil.setSession(req);
        try{
            Contract contract = contractService.loadContract(contractId);
            contractService.blockByOperator(contract);
            Client client = clientService.findClientByNumber(contract.getNumber());
            req.setAttribute("client", client);
            req.setAttribute("pagename", PageName.CLIENT.toString());
            req.setAttribute("successmessage", "Contract " + contract.getNumber() + " blocked by operator.");
            logger.info("Contract " + contract + " is blocked by operator.");
            return "client/client";
        }catch (ECareException ecx){
            req.setAttribute("client", contractService.loadContract(contractId).getClient());
            req.setAttribute("pagename", PageName.CLIENT.toString());
            req.setAttribute("errormessage", ecx.getMessage());
            return "client/client";
        }
    }

    @RequestMapping(value = "/unblockByOperator", method = RequestMethod.POST)
    public String unblockByOperator(HttpServletRequest req) {
        long contractId = Long.valueOf(req.getParameter("id"));
        ControllerUtil.setSession(req);
        try{
            Contract contract = contractService.loadContract(contractId);
            contractService.unblockByOperator(contract);
            Client client = clientService.findClientByNumber(contract.getNumber());
            req.setAttribute("client", client);
            req.setAttribute("pagename", PageName.CLIENT.toString());
            req.setAttribute("successmessage", "Contract " + contract.getNumber() + " unblocked by operator.");
            logger.info("Contract " + contract + " is unblocked by operator.");
            return "client/client";
        }catch (ECareException ecx){
            req.setAttribute("client", contractService.loadContract(contractId).getClient());
            req.setAttribute("pagename", PageName.CLIENT.toString());
            req.setAttribute("errormessage", ecx.getMessage());
            return "client/client";
        }
    }

    @RequestMapping(value = "/blockByClient", method = RequestMethod.POST)
    public String blockByClient(HttpServletRequest req) {
        long contractId = Long.valueOf(req.getParameter("id"));
        ControllerUtil.setSession(req);
        try{
            Contract contract = contractService.loadContract(contractId);
            contractService.blockByClient(contract);
            Client client = clientService.findClientByNumber(contract.getNumber());
            req.setAttribute("client", client);
            req.setAttribute("pagename", PageName.CLIENT.toString());
            req.setAttribute("successmessage", "Contract " + contract.getNumber() + " blocked by client.");
            logger.info("Contract " + contract + " is blocked by client.");
            return "client/client";
        }catch (ECareException ecx){
            req.setAttribute("client", contractService.loadContract(contractId).getClient());
            req.setAttribute("pagename", PageName.CLIENT.toString());
            req.setAttribute("errormessage", ecx.getMessage());
            return "client/client";
        }
    }

    @RequestMapping(value = "/unblockByClient", method = RequestMethod.POST)
    public String unblockByClient(HttpServletRequest req) {
        long contractId = Long.valueOf(req.getParameter("id"));
        ControllerUtil.setSession(req);
        try{
            Contract contract = contractService.loadContract(contractId);
            contractService.unblockByClient(contract);
            Client client = clientService.findClientByNumber(contract.getNumber());
            req.setAttribute("client", client);
            req.setAttribute("pagename", PageName.CLIENT.toString());
            req.setAttribute("successmessage", "Contract " + contract.getNumber() + " unblocked by client.");
            logger.info("Contract " + contract + " is unblocked by client.");
            return "client/client";
        }catch (ECareException ecx){
            req.setAttribute("client", contractService.loadContract(contractId).getClient());
            req.setAttribute("pagename", PageName.CLIENT.toString());
            req.setAttribute("errormessage", ecx.getMessage());
            return "client/client";
        }
    }

    @RequestMapping(value = "/changeTariff", method = RequestMethod.POST)
    public String changeTariff(HttpServletRequest req) {
        long contractId = Long.valueOf(req.getParameter("id"));
        ControllerUtil.setSession(req);
        Contract contract = contractService.loadContract(contractId);
        List<Tariff> tariffs = tariffService.getAllTariffs();
        req.setAttribute("contract", contract);
        req.setAttribute("tariffs", tariffs);
        req.setAttribute("pagename", PageName.CHOOSE_TARIFF.toString());
        logger.info("User " + Session.getInstance().getRole() + " went to change tariff page for contract " + contract + ".");
        return "client/chooseTariff";
    }

    @RequestMapping(value = "/chooseTariff", method = RequestMethod.POST)
    public String chooseTariff(HttpServletRequest req) {
        long contractId = Long.valueOf(req.getParameter("contractId"));
        long tariffId = Long.valueOf(req.getParameter("tariffId"));
        ControllerUtil.setSession(req);
        Contract contract = null;
        try {
            contract = contractService.loadContract(contractId);
            Tariff tariff = tariffService.loadTariff(tariffId);
            List<Option> options = optionService.getAllOptionsForTariff(tariffId);
            req.setAttribute("contract", contract);
            req.setAttribute("tariff", tariff);
            req.setAttribute("options", options);
            req.setAttribute("pagename", PageName.CHOOSE_OPTIONS.toString());
            return "client/chooseOptions";
        } catch (ECareException ecx) {
            contract = contractService.loadContract(contractId);
            List<Tariff> tariffs = tariffService.getAllTariffs();
            req.setAttribute("contract", contract);
            req.setAttribute("tariffs", tariffs);
            req.setAttribute("pagename", PageName.CHOOSE_TARIFF.toString());
            req.setAttribute("errormessage", ecx.getMessage());
            return "client/chooseTariff";
        }
    }

    @RequestMapping(value = "/setNewTariff", method = RequestMethod.POST)
    public String setNewTariff(HttpServletRequest req) {
        long contractId = Long.valueOf(req.getParameter("contractId"));
        long tariffId = Long.valueOf(req.getParameter("tariffId"));
        String chosenOptionsArray[] = req.getParameterValues("options");
        ControllerUtil.setSession(req);
        Contract contract = contractService.loadContract(contractId);
        try {
            Tariff tariff = tariffService.loadTariff(tariffId);
            contract = contractService.setTariff(contract, tariff, chosenOptionsArray);
            req.setAttribute("contract", contract);
            req.setAttribute("client", contract.getClient());
            req.setAttribute("pagename", PageName.CONTRACT.toString());
            req.setAttribute("successmessage", "Tariff " + tariff.getTitle() + " is set to contract " + contract.getNumber() + ".");
            logger.info("In contract " + contract + "set new tariff " + tariff + ".");
            return "client/contract";
        } catch (ECareException ecx) {
            tariffId = Long.valueOf(req.getParameter("tariffId"));
            Tariff tariff = tariffService.loadTariff(tariffId);
            List<Option> options = optionService.getAllOptionsForTariff(tariffId);
            req.setAttribute("contract", contract);
            req.setAttribute("tariff", tariff);
            req.setAttribute("options", options);
            req.setAttribute("pagename", PageName.CHOOSE_OPTIONS);
            req.setAttribute("errormessage", ecx.getMessage());
            return "client/chooseOptions";
        }
    }
}