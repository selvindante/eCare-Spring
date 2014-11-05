package ru.tsystems.tsproject.ecare.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tsystems.tsproject.ecare.ECareException;
import ru.tsystems.tsproject.ecare.dao.TariffDao;
import ru.tsystems.tsproject.ecare.entities.Tariff;

import java.util.List;

/**
 * This class is the implementation of ITariffService for working with tariff DAO
 * and tariff entities.
 *
 * @author Starostin Konstantin
 * @see ru.tsystems.tsproject.ecare.service.ITariffService
 */
@Service
public class TariffService implements ITariffService {

    /*SQL tariff implementations of abstract DAO class*/
    private TariffDao trDao;

    /*Logger for tariff service operations*/
    private static Logger logger = Logger.getLogger(TariffService.class);

    /*Constructor of Tariff Service class*/
    @Autowired
    public TariffService(TariffDao trDAO) {
        this.trDao = trDAO;
    }

    /**
     * This method implements saving or updating of tariff in the database.
     *
     * @param tr tariff entity to be saved or updated.
     * @return saved or updated tariff entity.
     * @throws ECareException if an error occurred during saving or updating of entity
     * and DAO returns null.
     */
    @Override
    public Tariff saveOrUpdateTariff(Tariff tr) throws ECareException {
        logger.info("Save/update tariff " + tr + " in DB.");
        Tariff tariff = trDao.saveOrUpdate(tr);
        //If DAO returns null method will throws an ECareException.
        if (tariff == null) {
            ECareException ecx = new ECareException("Failed to save/update tariff " + tr + " in DB.");
            logger.error(ecx.getMessage(), ecx);
            throw ecx;
        }
        logger.info("Tariff " + tariff + " saved in DB.");
        //Else tariff will be saved and method returns tariff entity.
        return tariff;
    }

    /**
     * This method implements loading of tariff from the database.
     *
     * @param id tariff id for search that tariff in the database.
     * @return loaded tariff entity.
     * @throws ECareException if an error occurred during loading of entity
     * and DAO returns null.
     */
    @Override
    public Tariff loadTariff(long id) throws ECareException {
        logger.info("Load tariff with id: " + id + " from DB.");
        Tariff tr = trDao.load(id);
        //If DAO returns null method will throws an ECareException.
        if (tr == null) {
            ECareException ecx = new ECareException("Tariff with id = " + id + " not found.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        logger.info("Tariff " + tr + " loaded from DB.");
        //Else method returns tariff entity.
        return tr;
    }

    /**
     * This method implements deleting of tariff from the database.
     *
     * @param id tariff id for deleting that tariff from the database.
     * @throws ECareException if an error occurred during intermediate loading
     * of entity and DAO returns null.
     */
    @Override
    public void deleteTariff(long id) throws ECareException {
        logger.info("Delete tariff with id: " + id + " from DB.");
        Tariff tr = trDao.load(id);
        //If DAO returns null method will throws an ECareException.
        if (tr == null) {
            ECareException ecx = new ECareException("Tariff with id = " + id + " not exist.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        // Else tariff will be deleted from the database.
        trDao.delete(tr);
        logger.info("Tariff " + tr + " deleted from DB.");
    }

    /**
     * This method implements receiving of all options from the database.
     *
     * @return list of received tariffs.
     */
    @Override
    public List<Tariff> getAllTariffs() {
        logger.info("Get all tariffs from DB.");
        List<Tariff> tariffs = trDao.getAll();
        //If DAO returns null method will throws an ECareException.
        if (tariffs == null) {
            ECareException ecx = new ECareException("Failed to get all tariffs from DB.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        logger.info("All tariffs obtained from DB.");
        // Else method returns list of tariff entities.
        return tariffs;
    }

    /**
     * This method implements deleting of all tariffs from the database.
     */
    @Override
    public void deleteAllTariffs() {
        logger.info("Delete all tariffs from DB.");
        trDao.deleteAll();
        logger.info("All tariffs deleted from DB.");
    }

    /**
     * This method implements receiving number of all tariffs from the storage.
     *
     * @return number of tariffs in the storage.
     */
    @Override
    public long getNumberOfTariffs() {
        logger.info("Get number of tariffs in DB.");
        long number = trDao.size();
        logger.info(number + " of tariffs obtained fromDB.");
        return number;
    }
}
