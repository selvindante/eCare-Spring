package ru.tsystems.tsproject.ecare.dao;

import org.springframework.stereotype.Repository;
import ru.tsystems.tsproject.ecare.entities.Option;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Selvin
 * on 06.10.2014.
 */
@Repository("optionDao")
public class OptionDao extends AbstractDAO<Option> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Option doSaveOrUpdate(Option op) {
        return em.merge(op);
    }

    @Override
    public Option doLoad(long id) {
        return em.find(Option.class, id);
    }

    public Option findOptionByTitleAndTariffId(String title, long id) {
        Query query = em.createNamedQuery("Option.findOptionByTitleAndTariffId", Option.class);
        query.setParameter("title", title);
        query.setParameter("id", id);
        return (Option) query.getSingleResult();
    }

    @Override
    public void doDelete(Option op) {
        em.remove(op);
    }

    @Override
    public List<Option> doGetAll() {
        return em.createNamedQuery("Option.getAllOptions", Option.class).getResultList();
    }

    @Override
    public void doDeleteAll() {
        em.createNamedQuery("Option.deleteAllOptions").executeUpdate();
    }

    public List<Option> getAllOptionsForTariff(long id) {
        Query query = em.createNamedQuery("Option.getAllOptionsForTariff", Option.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public void deleteAllOptionsForTariff(long id) {
        Query query = em.createNamedQuery("Option.deleteAllOptionsForTariff");
        query.setParameter(1, id);
        query.executeUpdate();
    }

    @Override
    public long doSize() {
        return ((Number)em.createNamedQuery("Option.size").getSingleResult()).longValue();
    }
}
