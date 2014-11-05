package ru.tsystems.tsproject.ecare.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tsystems.tsproject.ecare.ECareException;
import ru.tsystems.tsproject.ecare.dao.ClientDao;
import ru.tsystems.tsproject.ecare.entities.Client;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * This class is the implementation of IClientService for working with client DAO
 * and client entities.
 *
 * @author Starostin Konstantin
 * @see ru.tsystems.tsproject.ecare.service.IClientService
 */
@Service("clientService")
public class ClientService implements IClientService {

    /*SQL client implementations of DAO class*/
    private ClientDao clDao;

    /*Logger for client service operations*/
    private static Logger logger = Logger.getLogger(ClientService.class);

    /*Constructor of Client Service class*/
    @Autowired
    public ClientService(ClientDao clDAO) {
        this.clDao = clDAO;
    }

    /**
     * Method implements saving or updating of clients in the database.
     *
     * @param cl client entity to be saved or updated.
     * @return saved or updated client entity.
     * @throws ECareException if an error occurred during saving or updating of entity
     * and DAO returns null.
     */
    @Override
    public Client saveOrUpdateClient(Client cl) throws ECareException {
        logger.info("Save/update client " + cl + " in DB.");
        Client client = clDao.saveOrUpdate(cl);
        //If DAO returns null method will throws an ECareException
        if(client == null) {
            ECareException ecx = new ECareException("Failed to save/update client " + cl + " in DB.");
            logger.error(ecx.getMessage(), ecx);
            throw ecx;
        }
        logger.info("Client " + client + " saved/updated in DB.");
        //else client will be saved and method returns client entity
        return client;
    }

    /**
     * Method implements loading of clients from the database.
     *
     * @param id client id for search that client in the database.
     * @return loaded client entity.
     * @throws ECareException if an error occurred during loading of entity
     * and DAO returns null.
     */
    @Override
    public Client loadClient(long id) throws ECareException {
        logger.info("Load client with id: " + id + " from DB.");
        Client cl = clDao.load(id);
        //If DAO returns null method will throws an ECareException
        if(cl == null) {
            ECareException ecx = new ECareException("Client with id = " + id + " not found in DB.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        logger.info("Client " + cl + " loaded from DB.");
        //else method returns client entity
        return cl;
    }

    /**
     * Method implements finding of clients by their login and password in
     * the database.
     *
     * @param login client login for search that client in the database.
     * @param password client password for search that client in the database.
     * @return found client entity.
     * @throws ECareException if DAO returns NoResultException during finding of client
     * in the database.
     */
    @Override
    public Client findClient(String login, String password) throws ECareException {
        logger.info("Find client with login: " + login + " and password:" + password + " in DB.");
        Client cl = null;
        try {
            // Searching of client in the database by DAO method.
            cl = clDao.findClientByLoginAndPassword(login, password);
            // If client does not exist in database, block try catches the NoResultException and
            // throws an ECareException.
        } catch(NoResultException nrx) {
            ECareException ecx = new ECareException("Incorrect login/password or client does not exist.", nrx);
            logger.warn(ecx.getMessage(), nrx);
            throw ecx;
        }
        logger.info("Client " + cl + " found and loaded from DB.");
        return cl;
    }

    /**
     * Method implements finding of clients by their telephone number in the database.
     *
     * @param number telephone number of client for search that client in the database.
     * @return found client entity.
     * @throws ECareException if DAO returns NoResultException during finding of client
     * in the database.
     */
    @Override
    public Client findClientByNumber(long number) throws ECareException {
        logger.info("Find client with telephone number: " + number + " in DB.");
        Client cl = null;
        try {
            // Search of client in the database by DAO method.
            cl = clDao.findClientByNumber(number);
            // If client does not exist in database, block try catches the NoResultException and
            // throws an ECareException.
        } catch(NoResultException nrx) {
            ECareException ecx = new ECareException("Client with number: " + number + " not found.", nrx);
            logger.warn(ecx.getMessage(), nrx);
            throw ecx;
        }
        logger.info("Client " + cl + " found and loaded from DB.");
        return cl;
    }

    /**
     * Method implements deleting of clients from the database.
     *
     * @param id client id for deleting that client from the database.
     * @throws ECareException if an error occurred during intermediate loading
     * of entity and DAO returns null.
     */
    @Override
    public void deleteClient(long id) throws ECareException {
        logger.info("Delete client with id: " + id + " from DB.");
        Client cl = clDao.load(id);
        //If DAO returns null method will throws an ECareException.
        if(cl == null) {
            ECareException ecx = new ECareException("Client with id = " + id + " not exist.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        // Else client will be deleted from the database.
        clDao.delete(cl);
        logger.info("Client " + cl + " deleted from DB.");
    }

    /**
     * Method implements receiving of all clients from the database.
     *
     * @return list of received clients.
     * @throws ECareException if an error occurred during receiving of entities
     * and DAO returns null.
     */
    @Override
    public List<Client> getAllClients() throws ECareException {
        logger.info("Get all clients from DB.");
        List<Client> clients = clDao.getAll();
        //If DAO returns null method will throws an ECareException.
        if(clients == null) {
            ECareException ecx = new ECareException("Failed to get all clients from DB.");
            logger.error(ecx.getMessage(), ecx);
            throw ecx;
        }
        logger.info("All clients obtained from DB.");
        // Else method returns list of client entities
        return clients;
    }

    /**
     * Method implements deleting of all clients from the database.
     */
    @Override
    public void deleteAllClients() {
        logger.info("Delete all clients from DB.");
        clDao.deleteAll();
        logger.info("All clients deleted from DB.");
    }

    /**
     * Method implements receiving number of all clients from the database.
     *
     * @return number of clients in the database.
     */
    @Override
    public long getNumberOfClients() {
        logger.info("Get number of clients in DB.");
        long number = clDao.size();
        logger.info(number + "of clients obtained fromDB.");
        return number;
    }

    /**
     * This method implements searching of client in database by client login.
     *
     * @param login client login
     * @return true if client with input login exist, or false if client not exist.
     */
    @Override
    public boolean existLogin(String login) {
        logger.info("Find client with login: " + login + " in DB.");
        Client cl = null;
        try {
            // Search of client in the database by DAO method.
            cl = clDao.findClientByLogin(login);
            // If client does not exist in database, block try catches the NoResultException and
            // return false.
        } catch(NoResultException nrx) {
            logger.warn("Client with login: " + login + " does not exist.");
            return false;
        }
        logger.info("Client " + cl + " found in DB.");
        // Else, if client exist and loaded, method return true.
        return true;
    }
}
