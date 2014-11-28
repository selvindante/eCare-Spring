package ru.tsystems.tsproject.ecare.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.tsproject.ecare.ECareException;
import ru.tsystems.tsproject.ecare.dao.ContractDao;
import ru.tsystems.tsproject.ecare.entities.Client;
import ru.tsystems.tsproject.ecare.entities.Contract;
import ru.tsystems.tsproject.ecare.entities.Option;
import ru.tsystems.tsproject.ecare.entities.Tariff;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is the implementation of IContractService for working with contract DAO
 * and contract entities.
 *
 * @author Starostin Konstantin
 * @see ru.tsystems.tsproject.ecare.service.IContractService
 */
@Service
public class ContractService implements IContractService {

    /*SQL contract implementations of DAO class*/
    private ContractDao cnDao;

    /*Client service instance for some methods of working with client amount in contract service*/
    private final IClientService clientService;

    /*Tariff service instance for some methods of working with tariff service*/
    private final ITariffService tariffService;

    /*Option service instance for some methods of working with option service*/
    private final IOptionService optionService;

    /*Logger for contract service operations*/
    private static Logger logger = Logger.getLogger(ContractService.class);

    /*Constructor of Contract Service class*/
    @Autowired
    public ContractService(ContractDao cnDAO, IClientService clientService, IOptionService optionService, ITariffService tariffService) {
        this.cnDao = cnDAO;
        this.clientService = clientService;
        this.optionService = optionService;
        this.tariffService = tariffService;
    }

    /**
     * Method implements saving or updating of contracts in the database.
     *
     * @param cn contract entity to be saved or updated.
     * @return saved or updated contract entity.
     * @throws ECareException if an error occurred during saving or updating of entity
     * and DAO returns null.
     */
    @Override
    @Transactional
    public Contract saveOrUpdateContract(Contract cn) throws ECareException {
        logger.info("Save/update contract " + cn + " in DB.");
        Contract contract = cnDao.saveOrUpdate(cn);
        //If DAO returns null method will throws an ECareException
        if (contract == null) {
            ECareException ecx = new ECareException("Failed to save/update contract " + cn + " in DB.");
            logger.error(ecx.getMessage(), ecx);
            throw ecx;
        }
        logger.info("Contract " + contract + " saved/updated in DB.");
        //else contract will be saved and method returns contract entity
        return contract;
    }

    /**
     * This method implements loading of contracts from the database.
     *
     * @param id contract id for search that contract in the database.
     * @return loaded contract entity.
     * @throws ECareException if an error occurred during loading of entity
     * and DAO returns null.
     */
    @Override
    @Transactional
    public Contract loadContract(long id) throws ECareException {
        logger.info("Load contract with id: " + id + " from DB.");
        Contract cn = cnDao.load(id);
        //If DAO returns null method will throws an ECareException
        if (cn == null) {
            ECareException ecx = new ECareException("Contract with id = " + id + " not found in DB.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        logger.info("Contract " + cn + " loaded from DB.");
        //else method returns contract entity
        return cn;
    }

    /**
     * This method implements finding of contracts by telephone number in
     * the database.
     *
     * @param number contract number for search that contract in the database.
     * @return found contract entity.
     * @throws ECareException if DAO returns NoResultException during finding of contract
     * in the database.
     */
    @Override
    @Transactional
    public Contract findContractByNumber(long number) throws ECareException {
        logger.info("Find contract by telephone number: " + number + " in DB.");
        Contract cn = null;
        try {
            // Search of contract in the database by DAO method.
            cn = cnDao.findContractByNumber(number);
            // If contract does not exist in database, block try catches the NoResultException and
            // throws an ECareException.
        } catch (NoResultException nrx) {
            ECareException ecx = new ECareException("Contract with number: " + number + " not found.", nrx);
            logger.warn(ecx.getMessage(), nrx);
            throw ecx;
        }
        logger.info("Contract " + cn + " found and loaded from DB.");
        return cn;
    }

    /**
     * This method implements deleting of contract from the database.
     *
     * @param id contract id for deleting that contract from the database.
     * @throws ECareException if an error occurred during intermediate loading
     * of entity and DAO returns null.
     */
    @Override
    @Transactional
    public void deleteContract(long id) throws ECareException {
        logger.info("Delete contract with id: " + id + " from DB.");
        Contract cn = cnDao.load(id);
        //If DAO returns null method will throws an ECareException.
        if (cn == null) {
            ECareException ecx = new ECareException("Contract with id = " + id + " not exist.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        // Else contract will be deleted from the database.
        cnDao.delete(cn);
        logger.info("Contract " + cn + " deleted from DB.");
    }

    /**
     * This method implements receiving of all contracts from the database.
     *
     * @return list of received contracts.
     * @throws ECareException if an error occurred during receiving of entities
     * and DAO returns null.
     */
    @Override
    @Transactional
    public List<Contract> getAllContracts() throws ECareException {
        logger.info("Get all contracts from DB.");
        List<Contract> contracts = cnDao.getAll();
        //If DAO returns null method will throws an ECareException
        if (contracts == null) {
            ECareException ecx = new ECareException("Failed to get all contracts from DB.");
            logger.error(ecx.getMessage(), ecx);
            throw ecx;
        }
        logger.info("All contracts obtained from DB.");
        // Else method returns list of contract entities.
        return contracts;
    }

    /**
     * This method implements receiving of all contracts for client from the database.
     *
     * @param id client id for searching of all contracts for this client.
     * @return list of received contracts.
     * @throws ECareException if an error occurred during receiving of entities
     * and DAO returns null.
     */
    @Override
    @Transactional
    public List<Contract> getAllContractsForClient(long id) throws ECareException {
        logger.info("Get all contracts from DB for client with id: " + id + ".");
        List<Contract> contracts = cnDao.getAllContractsForClient(id);
        //If DAO returns null method will throws an ECareException
        if (contracts == null) {
            ECareException ecx = new ECareException("Failed to get all contracts from DB.");
            logger.error(ecx.getMessage(), ecx);
            throw ecx;
        }
        logger.info("All contracts for client id: " + id + " obtained from DB.");
        // Else method returns list of contract entities
        return contracts;
    }

    /**
     * This method implements deleting of all contracts from the database.
     */
    @Override
    public void deleteAllContracts() {
        logger.info("Delete all contracts from DB.");
        cnDao.deleteAll();
        logger.info("All contracts deleted from DB.");
    }

    /**
     * This method implements deleting of all contracts for client from the database.
     *
     * @param id client id for deleting of all contracts for this client
     */
    @Override
    @Transactional
    public void deleteAllContractsForClient(long id) {
        logger.info("Delete all contracts from DB for client with id: " + id + ".");
        cnDao.deleteAllContractsForClient(id);
        logger.info("All contracts for client id: " + id + " deleted from DB.");
    }

    /**
     * This method implements receiving number of all contracts from the database.
     *
     * @return number of contracts in the database.
     */
    @Override
    @Transactional
    public long getNumberOfContracts() {
        logger.info("Get number of contracts in DB.");
        long number = cnDao.size();
        logger.info(number + "of contracts obtained from DB.");
        return number;
    }

    /**
     * This method implements receiving of info about block by client status of contract.
     *
     * @param cn contract entity for receiving block status.
     * @return true if contract blocked by client or false if contract not blocked by client.
     */
    @Override
    public boolean isBlockedByClient(Contract cn) {
        logger.info("Get info about blocking of contract by client.");
        return cn.isBlockedByClient();
    }

    /**
     * This method implements receiving of info about block by operator status of contract.
     *
     * @param cn contract entity for receiving block status.
     * @return true if contract blocked by operator or false if contract not blocked by operator.
     */
    @Override
    public boolean isBlockedByOperator(Contract cn) {
        logger.info("Get info about blocking of contract by operator.");
        return cn.isBlockedByClient();
    }

    /**
     * This method implements blocking of contract by client.
     *
     * @param cn contract entity that must be blocked by client.
     * @throws ECareException if that contract is already blocked by client or
     * blocked by operator.
     */
    @Override
    public void blockByClient(Contract cn) throws ECareException {
        logger.info("Block of contract by client.");
        //If contract not blocked by operator.
        if(!cn.isBlockedByOperator()) {
            //If contract not blocked by client already.
            if(!cn.isBlockedByClient()) {
                cn.setBlockByClient(true);
                Client client = cn.getClient();
                client.getContracts().add(cn);
                clientService.saveOrUpdateClient(client);
                logger.info("Contract " + cn.getId() + " is blocked by client.");
            }
            else {
                ECareException ecx = new ECareException("Contract " + cn.getId() + " is already blocked by client.");
                logger.warn(ecx.getMessage(), ecx);
                throw ecx;
            }
        }
        else {
            ECareException ecx = new ECareException("Contract " + cn.getId() + " is blocked by operator.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
    }

    /**
     * This method implements unblocking of contract by client.
     *
     * @param cn contract entity that must be unblocked by client.
     * @throws ECareException if that contract is already unblocked by client or
     * blocked by operator.
     */
    @Override
    public void unblockByClient(Contract cn) throws ECareException {
        logger.info("Unblock of contract by client.");
        //If contract not blocked by operator.
        if(!cn.isBlockedByOperator()) {
            //If contract blocked by client.
            if(cn.isBlockedByClient()) {
                cn.setBlockByClient(false);
                Client client = cn.getClient();
                client.getContracts().add(cn);
                clientService.saveOrUpdateClient(client);
                logger.info("Contract " + cn.getId() + " is unblocked by client.");
            }
            else {
                ECareException ecx = new ECareException("Contract " + cn.getId() + " is already unblocked by client.");
                logger.warn(ecx.getMessage(), ecx);
                throw ecx;
            }
        }
        else {
            ECareException ecx = new ECareException("Contract " + cn.getId() + " is blocked by operator.");
            logger.info(ecx.getMessage(), ecx);
            throw ecx;
        }
    }

    /**
     * This method implements blocking of contract by operator.
     *
     * @param cn contract entity that must be blocked by operator.
     * @throws ECareException if that contract is already blocked by operator.
     */
    @Override
    public void blockByOperator(Contract cn) throws ECareException {
        logger.info("Block of contract by operator.");
        //If contract not blocked by operator already.
        if(!cn.isBlockedByOperator()) {
            cn.setBlockByOperator(true);
            Client client = cn.getClient();
            client.getContracts().add(cn);
            clientService.saveOrUpdateClient(client);
            logger.info("Contract " + cn.getId() + " is blocked by operator.");
        }
        else {
            ECareException ecx = new ECareException("Contract " + cn.getId() + " is already blocked by operator.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
    }

    /**
     * This method implements unblocking of contract by operator.
     *
     * @param cn contract entity that must be unblocked by operator.
     * @throws ECareException if that contract is already unblocked by operator.
     */
    @Override
    public void unblockByOperator(Contract cn) throws ECareException {
        logger.info("Unblock of contract by operator.");
        //If contract blocked by operator.
        if(cn.isBlockedByOperator()) {
            cn.setBlockByOperator(false);
            Client client = cn.getClient();
            client.getContracts().add(cn);
            clientService.saveOrUpdateClient(client);
            logger.info("Contract " + cn.getId() + " is unblocked by operator.");
        }
        else {
            ECareException ecx = new ECareException("Contract " + cn.getId() + " is already unblocked by operator.");
            logger.warn(ecx.getMessage());
            throw ecx;
        }
    }

    /**
     * This method implements connecting of option in contract (enabling of option
     * in contract).
     *
     * @param cn contract entity in which must be enabled option.
     * @param op option entity which must be enabled.
     * @return contract entity with enabled option.
     * @throws ECareException if connected option is incompatible with another option,
     * enabled in that contract, or if that option already enabled in contract.
     */
    @Override
    @Transactional
    public Contract enableOption(Contract cn, Option op) throws ECareException {
        logger.info("Enable option id: " + op.getId() + " in contract id: " + cn.getId() + ".");
        // Set current enabled options for contract in separated array list.
        List<Option> currentOptions = new ArrayList<>(cn.getOptions());
        // Get dependent client entity for contract.
        Client client = cn.getClient();
        //Check for incompatibility
        if(cn.getOptions().size() != 0) {
            for(Option o: cn.getOptions()) {
                // If chosen option are incompatible with any of enabled options for contract tariff -> ecx.
                if(o.getIncompatibleOptions().contains(op)) {
                    ECareException ecx = new ECareException("Option " + op.getTitle() + " is incompatible with option "
                                                                + o.getTitle() + " in contract " + cn.getNumber() + ".");
                    logger.warn(ecx.getMessage(), ecx);
                    throw ecx;
                }
            }
        }
        // If chosen option not enabled yet for contract tariff.
        if(!cn.getOptions().contains(op)) {
            cn.addOption(op);
            // If chosen option not been enabled for contract tariff in the previous time -> withdrawal for client.
            if(!currentOptions.contains(op)) {
                logger.info("Withdrawing of option id: " + op.getId() + " cost of connection: " +op.getPrice()
                             + " from amount of client id: " + client.getId() + ".");
                client.setAmount(client.getAmount() - op.getCostOfConnection());
                logger.info("Withdrawing completed. Client amount: " + client.getAmount() + ".");
            }
            logger.info("Option id: " + op.getId() + " enabled in contract id: " + cn.getId() + ".");
            for(Option dependentOption: op.getDependentOptions()) {
                // For every dependent option from chosen option: if contract not contains already that dependent option - > add.
                if(!cn.getOptions().contains(dependentOption)) {
                    cn.addOption(dependentOption);
                    // For every dependent option from chosen option: if dependent option not been enabled for contract
                    // tariff in the previous time -> withdrawal for client.
                    if(!currentOptions.contains(dependentOption)) {
                        logger.info("Withdrawing of dependent option id: " + dependentOption.getId() + " cost of connection "
                                    + dependentOption.getPrice() + " from amount of client id: " + client.getId() + ".");
                        client.setAmount(client.getAmount() - dependentOption.getCostOfConnection());
                        logger.info("Withdrawing completed. Client amount: " + client.getAmount() + ".");
                    }
                    logger.info("Dependent option id: " + dependentOption.getId() + " enabled in contract id: " + cn.getId() + ".");
                }
            }
        }
        else {
            ECareException ecx = new ECareException("Option " + op.getTitle() + " is already enabled in contract " + cn.getNumber() + ".");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        // Updating of client.
        clientService.saveOrUpdateClient(client);
        return cn;
    }

    /**
     * This method implements disabling of option in contract
     *
     * @param cn contract entity in which must be disabled option.
     * @param op option entity which must be disabled.
     * @return contract entity with disabled option.
     * @throws ECareException if that option is not enabled in contract.
     */
    @Override
    @Transactional
    public Contract disableOption(Contract cn, Option op) throws ECareException {
        logger.info("Disable option id: " + op.getId() + " in contract id: " + cn.getId() + ".");
        // If contract contains such option -> remove.
        if(cn.getOptions().contains(op)) {
            cn.deleteOption(op);
            logger.info("Option id: " + op.getId() + " disabled in contract id: " + cn.getId() + ".");
            for(Option dependentOption: op.getDependentOptions()) {
                // For every dependent option from chosen option: if contract contains such option -> remove it.
                if(cn.getOptions().contains(dependentOption)) {
                    cn.deleteOption(dependentOption);
                    logger.info("Dependent option id: " + dependentOption.getId() + " disabled in contract id: " + cn.getId() + ".");
                }
            }
        }
        else {
            ECareException ecx = new ECareException("Option " + op.getId() + " is not enabled yet in contract " + cn.getId() + ".");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        return cn;
    }

    /**
     * This method implements setting of tariff in contract
     *
     * @param contract contract entity in which must be set option.
     * @param tariff tariff entity which must be set in contract
     * @return contract Contract with established tariff and options.
     */
    @Override
    @Transactional
    public Contract setTariff(Contract contract, Tariff tariff, String chosenOptionsArray[]) {
        logger.info("Set tariff: " + tariff.getId() + " in contract id: " + contract.getId() + ".");
        // Get current tariff from contract.
        Tariff currentTariff = contract.getTariff();
        // Get dependent client entity for contract.
        Client client = contract.getClient();
        contract.setTariff(tariff);
        // If chosen tariff not been enabled in contract in the previous time -> withdrawal for client.
        if(!tariff.equals(currentTariff)) {
            logger.info("Withdrawing of tariff id: " + tariff.getId() + " price " + tariff.getPrice() + " from amount of client id: " + client.getId() + ".");
            client.setAmount(client.getAmount() - tariff.getPrice());
            logger.info("Withdrawing completed. Client amount: " + client.getAmount() + ".");
        }
        // Updating of client.
        clientService.saveOrUpdateClient(client);
        logger.info("Tariff: " + tariff.getId() + " is set in contract id: " + contract.getId() + ".");
        // Clear old options in contract and set new options
        contract.getOptions().clear();
        if(chosenOptionsArray != null) {
            List<String> optionsId = Arrays.asList(chosenOptionsArray);
            long optionId;
            Option option = null;
            for(String stringId: optionsId) {
                optionId = Long.valueOf(stringId);
                option = optionService.loadOption(optionId);
                contract = enableOption(contract, option);
            }
        }
        //Updating of contract in DB.
        contract = saveOrUpdateContract(contract);
        return contract;
    }

    /**
     * This method implements setting of the default tariff in new contract.
     *
     * @param contract new contract.
     * @return contract with default tariff.
     */
    @Override
    @Transactional
    public Contract setDefaultTariff(Contract contract) {
        Tariff tariff = tariffService.loadTariff(1l);
        String optionId[] = {"1"};
        contract = setTariff(contract, tariff, optionId);
        return contract;
    }
}
