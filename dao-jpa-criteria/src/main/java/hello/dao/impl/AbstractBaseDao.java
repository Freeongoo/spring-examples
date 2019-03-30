package hello.dao.impl;

import hello.container.FieldHolder;
import hello.container.QueryParams;
import hello.dao.BaseDao;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.util.ObjectUtils.isEmpty;

public abstract class AbstractBaseDao<T, ID extends Serializable> implements BaseDao<T, ID> {

    protected abstract Class<T> getPersistentClass();

    @PersistenceContext
    private EntityManager em;

    protected Session getSession() {
        return em.unwrap(Session.class);
    }

    protected CriteriaBuilder getCriteriaBuilder() {
        return getSession().getCriteriaBuilder();
    }

    protected CriteriaQuery<T> getCriteriaQuery() {
        CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
        return criteriaBuilder.createQuery(getPersistentClass());
    }

    protected Root<T> getRoot(CriteriaQuery<T> criteriaQuery) {
        return criteriaQuery.from(getPersistentClass());
    }

    protected CriteriaQuery<T> getCriteriaQueryWithRoot() {
        CriteriaQuery<T> criteriaQuery = getCriteriaQuery();
        Root<T> root = getRoot(criteriaQuery);
        return criteriaQuery.select(root);
    }

    @Override
    public List<T> getAll() {
        return getSession()
                .createQuery(getCriteriaQueryWithRoot())
                .getResultList();
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

    @Override
    public List<T> getByProps(Map<String, List<?>> props) {
        return null;
    }

    @Override
    public List<T> getByProp(String fieldName, Object fieldValue) {
        return null;
    }

    @Override
    public List<T> getByFields(Collection<FieldHolder> fieldHolders) {
        return null;
    }

    @Override
    public List<T> universalQuery(Map<String, List<?>> fields, QueryParams queryParams) {
        return null;
    }


}
