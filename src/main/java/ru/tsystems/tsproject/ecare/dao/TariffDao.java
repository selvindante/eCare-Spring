package ru.tsystems.tsproject.ecare.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.tsproject.ecare.entities.Tariff;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Selvin
 * on 06.10.2014.
 */
@Repository("tariffDAO")
@Transactional(propagation = Propagation.REQUIRED)
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
    @SuppressWarnings("unchecked")
    public List<Tariff> doGetAll() {
        return em.createNamedQuery("Tariff.getAllTariffs", Tariff.class).getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doDeleteAll() {
        em.createNamedQuery("Tariff.deleteAllTariffs").executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public long doSize() {
        return ((Number)em.createNamedQuery("Tariff.size").getSingleResult()).longValue();
    }
}
