package ru.tsystems.tsproject.ecare.dao;

import org.springframework.stereotype.Repository;
import ru.tsystems.tsproject.ecare.entities.Tariff;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implementation for AbstractDAO class, which works with Tariff entity. Marked as repository for
 * Spring Framework.
 */
@Repository("tariffDAO")
public class TariffDao extends AbstractDAO<Tariff> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Tariff doSaveOrUpdate(Tariff tr) {
        return em.merge(tr);
    }

    @Override
    public Tariff doLoad(long id) {
        return em.find(Tariff.class, id);
    }

    @Override
    public void doDelete(Tariff tr) {
        em.remove(tr);
    }

    @Override
    public List<Tariff> doGetAll() {
        return em.createNamedQuery("Tariff.getAllTariffs", Tariff.class).getResultList();
    }

    @Override
    public void doDeleteAll() {
        em.createNamedQuery("Tariff.deleteAllTariffs").executeUpdate();
    }

    @Override
    public long doSize() {
        return ((Number)em.createNamedQuery("Tariff.size").getSingleResult()).longValue();
    }
}
