package ru.tsystems.tsproject.ecare.service;

import ru.tsystems.tsproject.ecare.entities.Tariff;

import java.util.List;

/**
 * Interface of service level for tariff entity. It is an intermediate level
 * between controllers and tariff DAO.
 *
 * @author Starostin Konstantin
 */

public interface ITariffService {

    /**
     * This method provides saving or updating of tariff in the storage.
     *
     * @param tr tariff entity to be saved or updated.
     * @return saved or updated tariff entity.
     */
    public Tariff saveOrUpdateTariff(Tariff tr);

    /**
     * This method provides loading of tariff from the storage.
     *
     * @param id tariff id for search that tariff in the storage.
     * @return loaded tariff entity.
     */
    public Tariff loadTariff(long id);

    /**
     * This method provides deleting of tariff from the storage.
     *
     * @param id tariff id for deleting that tariff from the storage.
     */
    public void deleteTariff(long id);

    /**
     * This method provides receiving of all options from the storage.
     *
     * @return list of received tariffs.
     */
    public List<Tariff> getAllTariffs();

    /**
     * This method provides deleting of all tariffs from the storage.
     */
    public void deleteAllTariffs();

    /**
     * This method provides receiving number of all tariffs from the storage.
     *
     * @return number of tariffs in the storage.
     */
    public long getNumberOfTariffs();
}
