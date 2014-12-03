package ru.tsystems.tsproject.ecare.dao;

import java.util.List;

/**
 * This class describes the abstract DAO class that provides for inheritors the CRUD methods.
 *
 * @param <T> type of processed entity in the inheritor.
 * @author Starostin Konstantin
 */
public abstract class AbstractDAO<T> {
    // Abstract methods for implementation it in inheritors.
    protected abstract T doSaveOrUpdate(T t);
    protected abstract T doLoad(long id);
    protected abstract void doDelete(T t);
    protected abstract List<T> doGetAll();
    protected abstract void doDeleteAll();
    protected abstract long doSize();

    /**
     * Method of saving new object or updating existing object in storage.
     *
     * @param t typed object.
     * @return updated or saved typed object.
     */
    public T saveOrUpdate(T t){
        return doSaveOrUpdate(t);
    }

    /**
     * Loading of object from storage.
     *
     * @param id identifier of object in storage.
     * @return loaded object.
     */
    public T load(long id) {
        return doLoad(id);
    }

    /**
     * Deleting of object from storage.
     *
     * @param t object for deleting.
     */
    public void delete(T t) {
        doDelete(t);
    }

    /**
     * Loading of all objects collection from storage.
     *
     * @return collection (list) of objects.
     */
    public List<T> getAll() {
        return doGetAll();
    }

    /**
     * Deleting of all objects from storage.
     */
    public void deleteAll() {
        doDeleteAll();
    }

    /**
     * Method returns the size of storage.
     *
     * @return size value.
     */
    public long size() {
        return doSize();
    }
}
