package be.kdg.trips.dao;

/**
 * Created by Matthias on 24/07/2015.
 */
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

public abstract class AbstractDao<PK extends Serializable, T> {
//T = placeholder for the actual class that will extend this AbstractDao
    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public AbstractDao(){
        this.persistentClass =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    public T getByKey(PK key) {
        return (T) getSession().get(persistentClass, key);
    }
// Persists zorgt et voor dat er niet direct een ID wordt gegeven, pas na een flush / commit / einde transactie. Zorgt er voor dat er buiten transactie geen insert wordt gedaan
    // bij attached objects werken ze hetzelfde, bij detached zal save een nieuwe rij aanmaken terwijl persist een exception zal gooien, wat beter is want de sessions is closed dus niet zeker of dit wel zal persisteren
    public void persist(T entity) {
        getSession().persist(entity);
    }

    public void delete(T entity) {
        getSession().delete(entity);
    }

    protected Criteria createEntityCriteria(){
        return getSession().createCriteria(persistentClass);
    }

}