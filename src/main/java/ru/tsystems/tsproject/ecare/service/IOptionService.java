package ru.tsystems.tsproject.ecare.service;

import ru.tsystems.tsproject.ecare.entities.Option;

import java.util.List;

/**
 * Interface of service level for option entity. It is an intermediate level
 * between controllers and option DAO.
 *
 * @author Starostin Konstantin
 */

public interface IOptionService {

    /**
     * This method provides saving or updating of option in the storage.
     *
     * @param op option entity to be saved or updated.
     * @return saved or updated option entity.
     */
    public Option saveOrUpdateOption(Option op);

    /**
     * This method provides loading of option from the storage.
     *
     * @param id option id for search that option in the storage.
     * @return loaded option entity.
     */
    public Option loadOption(long id);

    /**
     * This method provides deleting of option from the storage.
     *
     * @param id option id for deleting that option from the storage.
     */
    public void deleteOption(long id);

    /**
     * This method provides receiving of all options from the storage.
     *
     * @return list of received options.
     */
    public List<Option> getAllOptions();

    /**
     * This method provides receiving of all options for tariff from the storage.
     *
     * @param id contract id for searching of all options for this contract.
     * @return list of received options.
     */
    public List<Option> getAllOptionsForTariff(long id);

    /**
     * This method provides deleting of all options for tariff from the storage.
     *
     * @param id tariff id for deleting of all options for this tariff.
     */
    public void deleteAllOptionsForTariff(long id);

    /**
     * This method provides receiving number of all options from the storage.
     *
     * @return number of options in the storage.
     */
    public long getNumberOfOptions();

    /**
     * This method sets dependent option for current option.
     *
     * @param currentOption current option entity.
     * @param dependentOption option entity which must be sets as dependent for
     *                        current option.
     * @return current option entity with dependent option in list.
     */
    public Option setDependentOption(Option currentOption, Option dependentOption);

    /**
     * This method removes dependent option for current option.
     *
     * @param currentOption current option entity.
     * @param dependentOption option entity which must be removed from list of
     *                        dependent options for current option.
     * @return current option entity without dependent option in list.
     */
    public Option deleteDependentOption(Option currentOption, Option dependentOption);

    /**
     * This method removes all dependent options for current option.
     *
     * @param currentOption current option entity.
     */
    public void clearDependentOptions(Option currentOption);

    /**
     * This method sets incompatible option for current option.
     *
     * @param currentOption current option entity.
     * @param incompatibleOption option entity which must be sets as incompatible for
     *                        current option.
     * @return current option entity with incompatible option in list.
     */
    public Option setIncompatibleOption(Option currentOption, Option incompatibleOption);

    /**
     * This method removes incompatible option for current option.
     *
     * @param currentOption current option entity.
     * @param incompatibleOption option entity which must be removed from list of
     *                        incompatible options for current option.
     * @return current option entity without incompatible option in list.
     */
    public Option deleteIncompatibleOption(Option currentOption, Option incompatibleOption);

    /**
     * This method removes all incompatible options for current option.
     *
     * @param currentOption current option entity.
     */
    public void clearIncompatibleOptions(Option currentOption);
}
