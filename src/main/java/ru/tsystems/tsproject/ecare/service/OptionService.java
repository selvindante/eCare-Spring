package ru.tsystems.tsproject.ecare.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.tsproject.ecare.ECareException;
import ru.tsystems.tsproject.ecare.dao.OptionDao;
import ru.tsystems.tsproject.ecare.entities.Option;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * This class is the implementation of IOptionService for working with option DAO
 * and option entities.
 *
 * @author Starostin Konstantin
 * @see ru.tsystems.tsproject.ecare.service.IOptionService
 */
@Service
public class OptionService implements IOptionService {

    /*SQL option implementations of DAO class*/
    private OptionDao opDao;

    /*Logger for option service operations*/
    private static Logger logger = Logger.getLogger(OptionService.class);

    /*Constructor of Client Service class*/
    @Autowired
    public OptionService(OptionDao opDAO) {
        this.opDao = opDAO;
    }

    /**
     * This method implements saving or updating of option in the database.
     *
     * @param op option entity to be saved or updated.
     * @return saved or updated option entity.
     * @throws ECareException if an error occurred during saving or updating of entity
     * and DAO returns null.
     */
    @Override
    @Transactional
    public Option saveOrUpdateOption(Option op) throws ECareException {
        logger.info("Save/update option " + op + " in DB.");
        Option option = opDao.saveOrUpdate(op);
        //If DAO returns null method will throws an ECareException
        if (option == null) {
            ECareException ecx = new ECareException("Failed to save/update option " + op + " in DB.");
            logger.error(ecx.getMessage(), ecx);
            throw ecx;
        }
        logger.info("Option " + option + " saved in DB.");
        //else option will be saved and method returns option entity
        return option;
    }

    /**
     * This method implements loading of option from the database.
     *
     * @param id option id for search that option in the database.
     * @return loaded option entity.
     * @throws ECareException if an error occurred during loading of entity
     * and DAO returns null.
     */
    @Override
    @Transactional
    public Option loadOption(long id) throws ECareException {
        logger.info("Load option with id: " + id + " from DB.");
        Option op = opDao.load(id);
        //If DAO returns null method will throws an ECareException
        if (op == null) {
            ECareException ecx = new ECareException("Option with id = " + id + " not found in DB.");
            logger.warn(String.valueOf(ecx), ecx);
            throw ecx;
        }
        logger.info("Options " + op + " loaded from DB.");
        //else method returns option entity
        return op;
    }

    /**
     * This method implements deleting of option from the database.
     *
     * @param id option id for deleting that option from the database.
     * @throws ECareException if an error occurred during intermediate loading
     * of entity and DAO returns null.
     */
    @Override
    @Transactional
    public void deleteOption(long id) throws ECareException {
        logger.info("Delete option with id: " + id + " from DB.");
        Option op = opDao.load(id);
        //If DAO returns null method will throws an ECareException
        if (op == null) {
            ECareException ecx = new ECareException("Option with id = " + id + " not exist.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        // Else option will be deleted from the database.
        opDao.delete(op);
        logger.info("Option " + op + " deleted from DB.");
    }

    /**
     * This method implements receiving of all options for tariff from the database.
     *
     * @param id contract id for searching of all options for this contract.
     * @return list of received options.
     * @throws ECareException if an error occurred during receiving of entities
     * and DAO returns null.
     */
    @Override
    @Transactional
    public List<Option> getAllOptionsForTariff(long id) throws ECareException{
        logger.info("Get all options from DB for tariff with id: " + id + ".");
        List<Option> options = opDao.getAllOptionsForTariff(id);
        //If DAO returns null method will throws an ECareException
        if (options == null) {
            ECareException ecx = new ECareException("Failed to get all options from DB for tariff id: " + id + ".");
            logger.error(ecx.getMessage(), ecx);
            throw ecx;
        }
        logger.info("All options for tariff id: " + id + " obtained from DB.");
        // Else method returns list of option entities
        return options;
    }

    /**
     * This method implements deleting of all options for tariff from the database.
     *
     * @param id tariff id for deleting of all options for this tariff.
     */
    @Override
    @Transactional
    public void deleteAllOptionsForTariff(long id) {
        logger.info("Delete all options from DB for tariff with id: " + id + ".");
        opDao.deleteAllOptionsForTariff(id);
        logger.info("All options for tariff id: " + id + " deleted from DB.");
    }

    /**
     * This method implements receiving number of all options from the database.
     *
     * @return number of options in the storage.
     */
    @Override
    @Transactional
    public long getNumberOfOptions() {
        logger.info("Get number of options in DB.");
        long number = opDao.size();
        logger.info(number + "of options obtained from DB.");
        return number;
    }

    /**
     * This method sets dependent option for current option.
     *
     * @param currentOption current option entity.
     * @param dependentOption option entity which must be sets as dependent for
     *                        current option.
     * @return current option entity with dependent option in list.
     * @throws ECareException if this options already incompatible.
     */
    @Override
    @Transactional
    public Option setDependentOption(Option currentOption, Option dependentOption) throws ECareException {
        logger.info("Set dependency of option id: " + currentOption.getId() + " with option id: " + dependentOption.getId() + ".");
        // If current option not incompatible for chosen option.
        if(!currentOption.getIncompatibleOptions().contains(dependentOption)) {
            // If current option not linked with chosen option by dependency or current option is not chosen option.
            if(!currentOption.getDependentOptions().contains(dependentOption) && !currentOption.equals(dependentOption)) {
                currentOption.addDependentOption(dependentOption);
                logger.info("Option id: " + dependentOption.getId() + " is now dependent for option id: " + currentOption.getId() + ".");
                currentOption = saveOrUpdateOption(currentOption);
            }
        }
        else {
            ECareException ecx = new ECareException("Chosen options are incompatible.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        return currentOption;
    }

    /**
     * This method removes dependent option for current option.
     *
     * @param currentOption current option entity.
     * @param dependentOption option entity which must be removed from list of
     *                        dependent options for current option.
     * @return current option entity without dependent option in list.
     * @throws ECareException if current option not contains such dependence.
     */
    @Override
    @Transactional
    public Option deleteDependentOption(Option currentOption, Option dependentOption) throws ECareException {
        logger.info("Remove dependency of option id: " + currentOption.getId() + " with option id: " + dependentOption.getId() + ".");
        // If current option linked with chosen option by dependency.
        if(currentOption.getDependentOptions().contains(dependentOption)) {
            currentOption.deleteDependentOption(dependentOption);
            logger.info("Option id: " + dependentOption.getId() + " is now independent from option id: " + currentOption.getId() + ".");
            currentOption = saveOrUpdateOption(currentOption);
        }
        else {
            ECareException ecx = new ECareException("Option " + currentOption.getId() + " not contains such dependence.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        return currentOption;
    }

    /**
     * This method removes all dependent options for current option.
     *
     * @param currentOption current option entity.
     */
    @Override
    @Transactional
    public void clearDependentOptions(Option currentOption) {
        logger.info("Remove all dependent options from option id: " + currentOption.getId() + ".");
        Set<Option> options = currentOption.getDependentOptions();
        Collections.synchronizedSet(options);
        Iterator<Option> it = options.iterator();
        while(it.hasNext()) {
            // For every dependent option for current option: remove dependency.
            Option o = it.next();
            deleteDependentOption(o, currentOption);
            saveOrUpdateOption(o);
        }
        // Remove all dependent options for current option.
        currentOption.getDependentOptions().clear();
        logger.info("All dependent options removed from option id: " + currentOption.getId() + ".");
    }

    /**
     * This method sets incompatible option for current option.
     *
     * @param currentOption current option entity.
     * @param incompatibleOption option entity which must be sets as incompatible for
     *                        current option.
     * @return current option entity with incompatible option in list.
     * @throws ECareException if this options already dependent.
     */
    @Override
    @Transactional
    public Option setIncompatibleOption(Option currentOption, Option incompatibleOption) throws ECareException {
        logger.info("Set incompatibility of option id: " + currentOption.getId() + " with option id: " + incompatibleOption.getId() + ".");
        // If current option not dependent for chosen option.
        if(!currentOption.getDependentOptions().contains(incompatibleOption)) {
            // If current option not linked with chosen option by incompatibility or current option is not chosen option.
            if(!currentOption.getIncompatibleOptions().contains(incompatibleOption) && !currentOption.equals(incompatibleOption)) {
                currentOption.addIncompatibleOption(incompatibleOption);
                logger.info("Option id: " + currentOption.getId() + " is now incompatible with option id: " + incompatibleOption.getId() + ".");
                currentOption = saveOrUpdateOption(currentOption);
            }
        }
        else {
            ECareException ecx = new ECareException("Chosen options are dependent.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        return currentOption;
    }

    /**
     * This method removes incompatible option for current option.
     *
     * @param currentOption current option entity.
     * @param incompatibleOption option entity which must be removed from list of
     *                        incompatible options for current option.
     * @return current option entity without incompatible option in list.
     * @throws ECareException if current option not contains such incompatibility.
     */
    @Override
    @Transactional
    public Option deleteIncompatibleOption(Option currentOption, Option incompatibleOption) throws ECareException {
        logger.info("Remove incompatibility of option id: " + currentOption.getId() + " with option id: " + incompatibleOption.getId() + ".");
        // If current option linked with chosen option by incompatibility.
        if(currentOption.getIncompatibleOptions().contains(incompatibleOption)) {
            currentOption.deleteIncompatibleOption(incompatibleOption);
            logger.info("Option id: " + currentOption.getId() + " is not incompatible now with option id: " + incompatibleOption.getId() + ".");
            currentOption = saveOrUpdateOption(currentOption);
        }
        else {
            ECareException ecx = new ECareException("Option " + currentOption.getId() + " not contains such incompatibility.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        return currentOption;
    }

    /**
     * This method removes all incompatible options for current option.
     *
     * @param currentOption current option entity.
     */
    @Override
    @Transactional
    public void clearIncompatibleOptions(Option currentOption) {
        logger.info("Remove all incompatible options from option id: " + currentOption.getId() + ".");
        Set<Option> options = currentOption.getIncompatibleOptions();
        Collections.synchronizedSet(options);
        Iterator<Option> it = options.iterator();
        while(it.hasNext()) {
            // For every incompatible option for current option: remove incompatibility.
            Option o = it.next();
            deleteIncompatibleOption(o, currentOption);
            saveOrUpdateOption(o);
        }
        // Remove all incompatible options for current option.
        currentOption.getIncompatibleOptions().clear();
        logger.info("All incompatible options removed from option id: " + currentOption.getId() + ".");
    }
}
