package ru.tsystems.tsproject.ecare.service;

import ru.tsystems.tsproject.ecare.entities.Client;

import java.util.List;

/**
 * Interface of service level for client entity. It is an intermediate level
 * between controllers and client DAO.
 *
 * @author Starostin Konstantin
 */
public interface IClientService {

    /**
     * This method provides saving or updating of clients in the storage.
     *
     * @param cl client entity to be saved or updated.
     * @return saved or updated client entity.
     */
    public Client saveOrUpdateClient(Client cl);

    /**
     * This method provides loading of clients from the storage.
     *
     * @param id client id for search that client in the storage.
     * @return loaded client entity.
     */
    public Client loadClient(long id);

    /**
     * This method provides finding of clients by their login and password in
     * the storage.
     *
     * @param login client login for search that client in the storage.
     * @param password client password for search that client in the storage.
     * @return found client entity.
     */
    public Client findClient(String login, String password);

    /**
     * This method provides finding of clients by their telephone number in
     * the storage.
     *
     * @param number telephone number of client for search that client in the storage.
     * @return found client entity.
     */
    public Client findClientByNumber(long number);

    /**
     * This method provides deleting of clients from the storage.
     *
     * @param id client id for deleting that client from the storage.
     */
    public void deleteClient(long id);

    /**
     * This method provides receiving of all clients from the storage.
     *
     * @return list of received clients.
     */
    public List<Client> getAllClients();

    /**
     * This method provides deleting of all clients from the storage.
     */
    public void deleteAllClients();

    /**
     * This method provides receiving number of all clients from the storage.
     *
     * @return number of clients in the storage.
     */
    public long getNumberOfClients();

    /**
     * This method provides searching of client in storage by client login.
     *
     * @param login client login
     * @return true if client with input login exist, or false if client not exist.
     */
    public boolean existLogin(String login);
}
