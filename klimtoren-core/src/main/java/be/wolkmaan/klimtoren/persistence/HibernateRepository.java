/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.persistence;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author karl
 */
public abstract class HibernateRepository<T> implements Repository<T> {

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    @Override
    public void store(Object entity) {
        getSession().saveOrUpdate(entity);
    }
    @Override
    public T get(Serializable id) {
        return (T) getSession().get(genericClass(), id);
    }
    
    
    private Class<T> genericClass() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass()
                .getGenericSuperclass();

        @SuppressWarnings("unchecked")
        Class<T> ret = (Class<T>) parameterizedType.getActualTypeArguments()[0];

        return ret;
    }

}
