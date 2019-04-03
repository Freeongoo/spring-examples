package hello.dao.impl;

import hello.container.*;
import hello.dao.BaseDao;
import hello.service.CriteriaAliasService;
import hello.util.EntityFieldUtils;
import hello.util.ReflectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toSet;
import static org.springframework.util.ObjectUtils.isEmpty;

public abstract class AbstractBaseDao<T, ID extends Serializable> implements BaseDao<T, ID> {

    protected abstract Class<T> getPersistentClass();

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CriteriaAliasService criteriaAliasService;

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

    @Override
    public List<T> getByProp(String fieldName, Object fieldValue) {
        Map<String, List<?>> mapProps = new HashMap<>();
        mapProps.put(fieldName, singletonList(fieldValue));
        return getByProps(mapProps);
    }

    protected Criteria getCriteriaByProps(Map<String, List<?>> props) {
        Objects.requireNonNull(props, "Param 'props' cannot be null, sorry");

        Criteria criteria = createEntityCriteria();

        Set<String> criteriaAlias = criteriaAliasService.getCriteriaAlias(props);
        setCriteriaAlias(criteria, criteriaAlias);

        props.forEach((fieldName, values) -> createCriteriaByFieldNameAndValues(criteria, fieldName, values));
        return criteria;
    }

    private void setCriteriaAlias(Criteria criteria, Set<String> criteriaAlias) {
        if (isEmpty(criteriaAlias)) return;
        criteriaAlias.forEach(a -> criteria.createAlias(a, a));
    }

    protected void createCriteriaByFieldNameAndValues(Criteria criteria, String fieldName, List<?> values) {
        if (values.isEmpty()) return;

        if (EntityFieldUtils.isRelationField(fieldName)) {
            setCriteriaRelationField(criteria, fieldName, values);
            return;
        }

        Class<?> fieldType = ReflectionUtils.getFieldType(getPersistentClass(), fieldName);
        setCriteriaInByValuesWithCast(criteria, fieldName, values, fieldType);
    }

    private void setCriteriaRelationField(Criteria criteria, String fieldName, List<?> values) {
        String relationFieldAlias = EntityFieldUtils.getRelationFieldAlias(fieldName);
        String relationFieldName = EntityFieldUtils.getRelationFieldName(fieldName);

        Class<?> relationFieldClass = ReflectionUtils.getFieldType(getPersistentClass(), relationFieldAlias);
        Class<?> relationFieldType = ReflectionUtils.getFieldType(relationFieldClass, relationFieldName);

        setCriteriaInByValuesWithCast(criteria, fieldName, values, relationFieldType);
    }

    private void setCriteriaInByValuesWithCast(Criteria criteria, String fieldName, List<?> values, Class<?> fieldType) {
        Set<Object> castedValues = values.stream()
                .map(v -> ReflectionUtils.castFieldValueByType(fieldType, v))
                .collect(toSet());

        criteria.add(Restrictions.in(fieldName, castedValues));
    }

    @Override
    public List<T> getByFields(Collection<FieldHolder> fieldHolders) {
        Objects.requireNonNull(fieldHolders, "Param 'fieldHolders' cannot be null, sorry");

        if (isEmpty(fieldHolders)) {
            return emptyList();
        }

        Criteria criteria = createEntityCriteria();

        Set<String> criteriaAlias = criteriaAliasService.getCriteriaAlias(fieldHolders);
        setCriteriaAlias(criteria, criteriaAlias);

        for (FieldHolder fieldHolder : fieldHolders) {
            criteria = getCriteriaByFieldHolder(criteria, fieldHolder);
        }

        return criteria.list();
    }

    private Criteria getCriteriaByFieldHolder(Criteria criteria, FieldHolder fieldHolder) {
        Assert.notNull(fieldHolder.getName(), "Field name cannot be null");

        if (isEmpty(fieldHolder.getRelationFieldName())) {
            setCriteriaEqByFieldWithCast(criteria, fieldHolder);
            return criteria;
        }

        setCriteriaEqByRelationField(criteria, fieldHolder);

        return criteria;
    }

    @Override
    public List<T> universalQuery(Map<String, List<?>> fields, QueryParams queryParams) {
        Criteria criteria = getCriteriaByProps(fields);
        criteria = getCriteriaByQueryParams(criteria, queryParams);

        return criteria.list();
    }

    private Criteria getCriteriaByQueryParams(Criteria criteria, QueryParams queryParams) {
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

        return criteria;
    }

    /**
     * Important! equals without cast type - do the cast yourself
     *
     * @param criteria   criteria
     * @param fieldName  fieldName
     * @param fieldValue fieldValue
     */
    protected void setCriteriaEqByField(Criteria criteria, String fieldName, Object fieldValue) {
        criteria.add(Restrictions.eq(fieldName, fieldValue));
    }

    private void setCriteriaEqByFieldWithCast(Criteria criteria, FieldHolder fieldHolder) {
        setCriteriaEqByFieldWithCast(criteria, fieldHolder.getName(), fieldHolder.getValue());
    }

    protected Criteria setCriteriaEqByFieldWithCast(Criteria criteria, String fieldName, Object fieldValue) {
        Object fieldValueCasted = getCastedFieldValue(fieldName, fieldValue);

        criteria.add(Restrictions.eq(fieldName, fieldValueCasted));
        return criteria;
    }

    /**
     * Important return null - for correct query for criteria
     *
     * @param fieldName  fieldName
     * @param fieldValue fieldValue
     * @return Object or null
     */
    private Object getCastedFieldValue(String fieldName, Object fieldValue) {
        if (fieldValue == null) {
            return null;
        }
        return ReflectionUtils.castFieldValueByClass(getPersistentClass(), fieldName, fieldValue);
    }

    /**
     * Important! Before use, you must add an alias in the Criteria for relation field
     * but except for the field "id" - not need create alias
     *
     * @param criteria          criteria
     * @param fieldName         fieldName
     * @param fieldValue        fieldValue
     * @param relationFieldName relationFieldName
     * @return Criteria
     */
    protected void setCriteriaEqByRelationField(Criteria criteria, String fieldName, Object fieldValue, String relationFieldName) {
        Class<?> relationFieldClass = ReflectionUtils.getFieldType(getPersistentClass(), relationFieldName);
        Object valueFromRelationObject = ReflectionUtils.castFieldValueByClass(relationFieldClass, fieldName, fieldValue);

        setCriteriaEqByField(criteria, EntityFieldUtils.concat(relationFieldName, fieldName), valueFromRelationObject);
    }

    private void setCriteriaEqByRelationField(Criteria criteria, FieldHolder fieldHolder) {
        setCriteriaEqByRelationField(criteria, fieldHolder.getName(), fieldHolder.getValue(), fieldHolder.getRelationFieldName());
    }

    /**
     * @param criteria          criteria
     * @param idValue           idValue
     * @param relationFieldName relationFieldName
     */
    protected void setCriteriaEqByRelationId(Criteria criteria, Object idValue, String relationFieldName) {
        setCriteriaEqByRelationField(criteria, "id", idValue, relationFieldName);
    }
}
