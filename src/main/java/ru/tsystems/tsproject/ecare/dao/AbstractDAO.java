package ru.tsystems.tsproject.ecare.dao;

import java.util.List;

/**
 * Created by Selvin
 * on 15.10.2014.
 */

public abstract class AbstractDAO<T> {
    protected abstract T doSaveOrUpdate(T t);
    protected abstract T doLoad(long id);
    protected abstract void doDelete(T t);
    protected abstract List<T> doGetAll();
    protected abstract void doDeleteAll();
    protected abstract long doSize();

    public T saveOrUpdate(T t){
        return doSaveOrUpdate(t);
    }

    public T load(long id) {
        return doLoad(id);
    }

    public void delete(T t) {
        doDelete(t);
    }

    public List<T> getAll() {
        return doGetAll();
    }

    public void deleteAll() {
        doDeleteAll();
    }

    public long size() {
        return doSize();
    }
}
