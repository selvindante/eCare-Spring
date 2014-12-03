package ru.tsystems.tsproject.ecare.dao;

import org.springframework.stereotype.Repository;
import ru.tsystems.tsproject.ecare.entities.Contract;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Implementation for AbstractDAO class, which works with Contract entity. Marked as repository for
 * Spring Framework.
 */
@Repository("contractDao")
public class ContractDao extends AbstractDAO<Contract> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Contract doSaveOrUpdate(Contract cn) {
        return em.merge(cn);
    }

    @Override
    public Contract doLoad(long id) {
        return em.find(Contract.class, id);
    }

    public Contract findContractByNumber(long number) {
        Query query = em.createNamedQuery("Contract.findContractByNumber", Contract.class);
        query.setParameter("number", number);
        return (Contract) query.getSingleResult();
    }

    @Override
    public void doDelete(Contract cn) {
        em.remove(cn);
    }

    @Override
    public List<Contract> doGetAll() {
        return em.createNamedQuery("Contract.getAllContracts", Contract.class).getResultList();
    }

    public List<Contract> getAllContractsForClient(long id) {
        Query query = em.createNamedQuery("Contract.getAllContractsForClient", Contract.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public void doDeleteAll() {
        em.createNamedQuery("Contract.deleteAllContracts").executeUpdate();
    }

    public void deleteAllContractsForClient(long id) {
        Query query = em.createNamedQuery("Contract.deleteAllContractsForClient");
        query.setParameter(1, id);
        query.executeUpdate();
    }

    @Override
    public long doSize() {
        return ((Number)em.createNamedQuery("Contract.size").getSingleResult()).longValue();
    }
}
