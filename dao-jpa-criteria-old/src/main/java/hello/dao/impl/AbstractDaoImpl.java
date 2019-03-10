package hello.dao.impl;

import hello.dao.AbstractDao;
import org.hibernate.Criteria;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDaoImpl<T, ID extends Serializable> implements AbstractDao<T, ID> {

    protected abstract Class<T> getPersistentClass();

    @PersistenceContext
    private EntityManager em;

    protected Session getSession() {
        return em.unwrap(Session.class);
    }

    protected Criteria createEntityCriteria() {
        return getSession()
                .createCriteria(getPersistentClass())
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    }

    @Override
    public List<T> getAll() {
        return createEntityCriteria().list();
    }

    @Override
    public Optional<T> getById(ID id) {
        T entity = getSession().get(getPersistentClass(), id);
        return Optional.ofNullable(entity);
    }

    @Override
    public void persist(T entity) {
        getSession().persist(entity);
    }

    @Override
    public void saveOrUpdate(T entity) {
        getSession().saveOrUpdate(entity);
    }

    @Override
    public void delete(T entity) {
        getSession().delete(entity);
    }
}
