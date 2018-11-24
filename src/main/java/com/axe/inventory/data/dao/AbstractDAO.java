package com.axe.inventory.data.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase base de la capa de persistencia.
 *
 * @author carlos herrera
 * @param <T> tipoEntidad
 */
public abstract class AbstractDAO<T> {

    private final Class<T> entity;
    private static final Logger logger = LoggerFactory.getLogger( AbstractDAO.class );

    public abstract SessionFactory getSessionFactory();

    public AbstractDAO(Class<T> entity) {
        this.entity = entity;
    }

    public int save(T entity) {
        int result = 1;

        try {
            getSessionFactory().getCurrentSession().saveOrUpdate(entity);
            result = 0;
        } catch (Exception e) {
        	logger.error( "Error while trying save", e );
        }
        return result;
    }
    
    public void saveNoCatch( T entity ) {
    	getSessionFactory().getCurrentSession().saveOrUpdate(entity);
    }
    
    

    public int delete(T entity) {
        int result = 1;

        try {
            getSessionFactory().getCurrentSession().delete(entity);
            result = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public T find(Object id) {
        T row = null;

        try {
            Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entity);
            criteria.add(Restrictions.idEq(id));
            row = (T) criteria.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return row;
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        List<T> listRow = null;

        try {
            Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entity);
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            listRow = criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listRow;
    }
}
