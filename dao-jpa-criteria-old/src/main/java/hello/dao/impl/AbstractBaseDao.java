package hello.dao.impl;

import hello.container.FieldHolder;
import hello.container.OrderType;
import hello.container.QueryParams;
import hello.dao.BaseDao;
import hello.util.EntityFieldUtils;
import hello.util.ReflectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toSet;
import static org.springframework.util.ObjectUtils.isEmpty;

public abstract class AbstractBaseDao<T, ID extends Serializable> implements BaseDao<T, ID> {

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
        Criteria criteria = getCriteriaByProps(props);

        return criteria.list();
    }

    protected Criteria getCriteriaByProps(Map<String, List<?>> props) {
        Objects.requireNonNull(props, "Param 'props' cannot be null, sorry");

        Criteria criteria = createEntityCriteria();
        props.forEach((fieldName, values) -> createCriteriaByFieldNameAndValues(criteria, fieldName, values));
        return criteria;
    }

    protected void createCriteriaByFieldNameAndValues(Criteria criteria, String fieldName, List<?> values) {
        if (EntityFieldUtils.isRelationField(fieldName)) {
            if (values.size() > 1)
                throw new RuntimeException(String.format("For relation field '%s' must be only one value, for create join query", fieldName));

            getCriteriaEqByRelationId(criteria, values.get(0), EntityFieldUtils.getRelationFieldAlias(fieldName));
            return;
        }

        Class<?> fieldType = getFieldType(fieldName);

        if (values.isEmpty()) return;

        if (fieldType.isAssignableFrom(Boolean.class))
            criteria.add(Restrictions.in(fieldName, values.stream()
                    .map(x -> {
                        if (x instanceof String) {
                            String trimmedStr = ((String) x).trim();
                            return !trimmedStr.equals("") && !trimmedStr.equals("0") && !trimmedStr.toLowerCase().equals("false");
                        }
                        if (x instanceof Number) {
                            return !(x).equals(0);
                        }
                        return x;
                    })
                    .collect(toSet())));

        else if (fieldType.isAssignableFrom(Double.class))
            criteria.add(Restrictions.in(fieldName, values.stream()
                    .map(x -> {
                        if (x instanceof String) {
                            return Double.valueOf((String) x);
                        }
                        return ((Number)x).doubleValue();
                    })
                    .collect(toSet())));

        else if (fieldType.isAssignableFrom(Long.class))
            criteria.add(Restrictions.in(fieldName, values.stream()
                    .map(x -> {
                        if (x instanceof String) {
                            return Long.valueOf((String) x);
                        }
                        return ((Number)x).longValue();
                    })
                    .collect(toSet())));

        else if (fieldType.isAssignableFrom(Float.class))
            criteria.add(Restrictions.in(fieldName, values.stream()
                    .map(x -> {
                        if (x instanceof String) {
                            return Float.valueOf((String) x);
                        }
                        return ((Number)x).floatValue();
                    })
                    .collect(toSet())));

        else if (fieldType.isAssignableFrom(Integer.class))
            criteria.add(Restrictions.in(fieldName, values.stream()
                    .map(x -> {
                        if (x instanceof String) {
                            return Integer.valueOf((String) x);
                        }
                        return ((Number)x).intValue();
                    })
                    .collect(toSet())));

        else if (fieldType.isAssignableFrom(Short.class))
            criteria.add(Restrictions.in(fieldName, values.stream()
                    .map(x -> {
                        if (x instanceof String) {
                            return Short.valueOf((String) x);
                        }
                        return ((Number)x).shortValue();
                    })
                    .collect(toSet())));

        else
            criteria.add(Restrictions.in(fieldName, values));
    }

    @Override
    public List<T> getByFields(Collection<FieldHolder> fieldHolders) {
        Objects.requireNonNull(fieldHolders, "Param 'fieldHolders' cannot be null, sorry");

        if (isEmpty(fieldHolders)) {
            return emptyList();
        }

        Criteria criteria = createEntityCriteria();
        Set<String> aliasStore = new HashSet<>();
        for (FieldHolder fieldHolder : fieldHolders) {
            criteria = getCriteriaByFieldHolder(criteria, aliasStore, fieldHolder);
        }

        return criteria.list();
    }

    private Criteria getCriteriaByFieldHolder(Criteria criteria, Set<String> aliasStore, FieldHolder fieldHolder) {
        Assert.notNull(fieldHolder.getName(), "Field name cannot be null");

        if (isEmpty(fieldHolder.getRelationFieldName())) {
            criteria = getCriteriaEqByFieldWithCast(criteria, fieldHolder.getName(), fieldHolder.getValue());
            return criteria;
        }

        criteria = getCriteriaWithAliasIfNeeded(criteria, aliasStore, fieldHolder);
        criteria = getCriteriaEqByRelationField(criteria, fieldHolder.getName(), fieldHolder.getValue(), fieldHolder.getRelationFieldName());

        return criteria;
    }

    private Criteria getCriteriaWithAliasIfNeeded(Criteria criteria, Set<String> aliasStore, FieldHolder fieldHolder) {
        String relationFieldName = fieldHolder.getRelationFieldName();
        if (!aliasStore.contains(relationFieldName)) {
            criteria = criteria.createAlias(relationFieldName, relationFieldName);
            aliasStore.add(relationFieldName);
        }
        return criteria;
    }

    @Override
    public List<T> universalQuery(Map<String, List<?>> fields, QueryParams queryParams) {
        Criteria criteria = getCriteriaByProps(fields);

        if (queryParams.getSortBy() != null) {
            if (queryParams.getOrderType() == null || queryParams.getOrderType().equals(OrderType.ASC)) {
                criteria.addOrder(Order.asc(queryParams.getSortBy()));
            } else if (queryParams.getOrderType().equals(OrderType.DESC)) {
                criteria.addOrder(Order.desc(queryParams.getSortBy()));
            }
        }

        if (queryParams.getStart() != null)
            criteria.setFirstResult(queryParams.getStart());

        if (queryParams.getLimit() != null)
            criteria.setMaxResults(queryParams.getLimit());

        return criteria.list();
    }

    /**
     * Important! equals without cast type - do the cast yourself
     *
     * @param criteria criteria
     * @param fieldName fieldName
     * @param fieldValue fieldValue
     * @return Criteria
     */
    protected Criteria getCriteriaEqByField(Criteria criteria, String fieldName, Object fieldValue) {
        criteria.add(Restrictions.eq(fieldName, fieldValue));
        return criteria;
    }

    protected Criteria getCriteriaEqByFieldWithCast(Criteria criteria, String fieldName, Object fieldValue) {
        Object fieldValueCasted = fieldValue == null ? null : ReflectionUtils.castFieldValue(getPersistentClass(), fieldName, fieldValue);

        criteria.add(Restrictions.eq(fieldName, fieldValueCasted));
        return criteria;
    }

    /**
     * Important! Before use, you must add an alias in the criterion for relation field
     * except for the field "id" - not need create alias
     *
     * @param criteria criteria
     * @param fieldName fieldName
     * @param fieldValue fieldValue
     * @param relationFieldName relationFieldName
     * @return Criteria
     */
    protected Criteria getCriteriaEqByRelationField(Criteria criteria, String fieldName, Object fieldValue, String relationFieldName) {
        Class<?> relationFieldClass = getFieldType(relationFieldName);
        Object valueFromRelationObject = ReflectionUtils.castFieldValue(relationFieldClass, fieldName, fieldValue);

        return getCriteriaEqByField(criteria, EntityFieldUtils.concat(relationFieldName, fieldName), valueFromRelationObject);
    }

    /**
     * @param criteria criteria
     * @param idValue idValue
     * @param relationFieldName relationFieldName
     * @return Criteria
     */
    protected Criteria getCriteriaEqByRelationId(Criteria criteria, Object idValue, String relationFieldName) {
        return getCriteriaEqByRelationField(criteria, "id", idValue, relationFieldName);
    }

    private Class<?> getFieldType(String fieldName) {
        return getField(fieldName).getType();
    }

    private Field getField(String fieldName) {
        return ReflectionUtils.getField(getPersistentClass(), fieldName)
                .orElseThrow(() -> {
                    String msg = String.format("Cannot find field name: '%s' from class: '%s'", fieldName, getPersistentClass());
                    return new IllegalArgumentException(msg);
                });
    }
}
