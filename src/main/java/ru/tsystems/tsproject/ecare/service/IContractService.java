package ru.tsystems.tsproject.ecare.service;

import ru.tsystems.tsproject.ecare.entities.Contract;
import ru.tsystems.tsproject.ecare.entities.Option;
import ru.tsystems.tsproject.ecare.entities.Tariff;

import java.util.List;

/**
 * Interface of service level for contract entity. It is an intermediate level
 * between controllers and contract DAO.
 *
 * @author Starostin Konstantin
 */

public interface IContractService {

    /**
     * This method provides saving or updating of contracts in the storage.
     *
     * @param cn contract entity to be saved or updated.
     * @return saved or updated contract entity.
     */
    public Contract saveOrUpdateContract(Contract cn);

    /**
     * This method provides loading of contracts from the storage.
     *
     * @param id contract id for search that contract in the storage.
     * @return loaded contract entity.
     */
    public Contract loadContract(long id);

    /**
     * This method provides finding of contracts by telephone number in
     * the storage.
     *
     * @param number contract number for search that contract in the storage.
     * @return found contract entity.
     */
    public Contract findContractByNumber(long number);

    /**
     * This method provides deleting of contract from the storage.
     *
     * @param id contract id for deleting that contract from the storage.
     */
    public void deleteContract(long id);

    /**
     * This method provides receiving of all contracts from the storage.
     *
     * @return list of received contracts.
     */
    public List<Contract> getAllContracts();

    /**
     * This method provides receiving of all contracts for client from the storage.
     *
     * @param id client id for searching of all contracts for this client.
     * @return list of received contracts.
     */
    public List<Contract> getAllContractsForClient(long id);

    /**
     * This method provides deleting of all contracts from the storage.
     */
    public void deleteAllContracts();

    /**
     * This method provides deleting of all contracts for client from the storage.
     *
     * @param id client id for deleting of all contracts for this client.
     */
    public void deleteAllContractsForClient(long id);

    /**
     * This method provides receiving number of all contracts from the storage.
     *
     * @return number of contracts in the storage.
     */
    public long getNumberOfContracts();

    /**
     * This method provides receiving of info about block by client status of contract.
     *
     * @param cn contract entity for receiving block status.
     * @return true if contract blocked by client or false if contract not blocked by client.
     */
    public boolean isBlockedByClient(Contract cn);

    /**
     * This method provides receiving of info about block by operator status of contract.
     *
     * @param cn contract entity for receiving block status.
     * @return true if contract blocked by operator or false if contract not blocked by operator.
     */
    public boolean isBlockedByOperator(Contract cn);

    /**
     * This method provides blocking of contract by client.
     *
     * @param cn contract entity that must be blocked by client.
     */
    public void blockByClient(Contract cn);

    /**
     * This method provides unblocking of contract by client.
     *
     * @param cn contract entity that must be unblocked by client.
     */
    public void unblockByClient(Contract cn);

    /**
     * This method provides blocking of contract by operator.
     *
     * @param cn contract entity that must be blocked by operator.
     */
    public void blockByOperator(Contract cn);

    /**
     * This method provides unblocking of contract by operator.
     *
     * @param cn contract entity that must be unblocked by operator.
     */
    public void unblockByOperator(Contract cn);

    /**
     * This method provides connecting of option in contract (enabling of option
     * in contract).
     *
     * @param cn contract entity in which must be enabled option.
     * @param op option entity which must be enabled.
     * @return contract entity with enabled option.
     */
    public Contract enableOption(Contract cn, Option op);

    /**
     * This method provides disabling of option in contract
     *
     * @param cn contract entity in which must be disabled option.
     * @param op option entity which must be disabled.
     * @return contract entity with disabled option.
     */
    public Contract disableOption(Contract cn, Option op);

    /**
     * This method provides setting of tariff in contract
     *
     * @param contract contract entity in which must be set option.
     * @param tariff tariff entity which must be set in contract
     */
    public void setTariff(Contract contract, Tariff tariff);
}
