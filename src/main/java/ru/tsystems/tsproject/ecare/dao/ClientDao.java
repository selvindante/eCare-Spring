package ru.tsystems.tsproject.ecare.dao;

import org.springframework.stereotype.Repository;
import ru.tsystems.tsproject.ecare.entities.Client;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Implementation for AbstractDAO class, that works with Client entity. Marked as repository for
 * Spring Framework.
 */
@Repository("clientDao")
public class ClientDao extends AbstractDAO<Client> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Client doSaveOrUpdate(Client cl) {
        return em.merge(cl);
    }

    @Override
    public Client doLoad(long id) {
        return em.find(Client.class, id);
    }

    public Client findClientByLoginAndPassword(String login, String password) {
        Query query = em.createNamedQuery("Client.findClientByLoginAndPassword", Client.class);
        query.setParameter("login", login);
        query.setParameter("password", password);
        return (Client) query.getSingleResult();
    }

    public Client findClientByNumber(long number) {
        Query query = em.createNamedQuery("Client.findClientByNumber", Client.class);
        query.setParameter("number", number);
        return (Client) query.getSingleResult();
    }

    @Override
    public void doDelete(Client cl) {
        em.remove(cl);
    }

    @Override
    public List<Client> doGetAll() {
        return em.createNamedQuery("Client.getAllClients", Client.class).getResultList();
    }

    @Override
    public void doDeleteAll() {
        em.createNamedQuery("Client.deleteAllClients").executeUpdate();
    }

    @Override
    public long doSize() {
        return ((Number)em.createNamedQuery("Client.size").getSingleResult()).longValue();
    }

    public Client findClientByLogin(String login) {
        Query query = em.createNamedQuery("Client.findClientByLogin", Client.class);
        query.setParameter("login", login);
        return (Client) query.getSingleResult();
    }
}
