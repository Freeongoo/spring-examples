package hello.dao.impl;

import hello.dao.AbstractDao;
import hello.util.ReflectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<T> getByProps(Map<String, List<?>> props) {
        Criteria criteria = createEntityCriteria();
        props.forEach((fieldName, values) -> createCriteriaByFieldNameAndValues(criteria, fieldName, values));

        return criteria.list();
    }

    private void createCriteriaByFieldNameAndValues(Criteria criteria, String fieldName, List<?> values) {
        Class<?> fieldType = getFieldType(fieldName);

        if (values.isEmpty()) return;

        if (fieldType.isAssignableFrom(Double.class))
            criteria.add(Restrictions.in(fieldName, values.stream().map(x -> ((Number)x).doubleValue()).collect(Collectors.toSet())));

        else if (fieldType.isAssignableFrom(Long.class))
            criteria.add(Restrictions.in(fieldName, values.stream().map(x -> ((Number)x).longValue()).collect(Collectors.toSet())));

        else if (fieldType.isAssignableFrom(Float.class))
            criteria.add(Restrictions.in(fieldName, values.stream().map(x -> ((Number)x).floatValue()).collect(Collectors.toSet())));

        else if (fieldType.isAssignableFrom(Integer.class))
            criteria.add(Restrictions.in(fieldName, values.stream().map(x -> ((Number)x).intValue()).collect(Collectors.toSet())));

        else if (fieldType.isAssignableFrom(Short.class))
            criteria.add(Restrictions.in(fieldName, values.stream().map(x -> ((Number)x).shortValue()).collect(Collectors.toSet())));

        else
            criteria.add(Restrictions.in(fieldName, values));
    }

    private Class<?> getFieldType(String fieldName) {
        Field field = ReflectionUtils.getField(getPersistentClass(), fieldName)
                .orElseThrow(() -> new RuntimeException(String.format("Cannot find fieldName: '%s'", fieldName)));
        return field.getType();
    }
}
