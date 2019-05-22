package hello.dao.impl;

import hello.container.*;
import hello.dao.BaseDao;
import hello.util.EntityFieldUtils;
import hello.util.ReflectionUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.*;

import static hello.util.SQLInjectionEscaper.escapeString;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.*;
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
        CriteriaQuery<T> criteria = getCriteriaByProps(props);

        return getSession().createQuery(criteria).getResultList();
    }

    @Override
    public List<T> getByProp(String fieldName, Object fieldValue) {
        Map<String, List<?>> mapProps = new HashMap<>();
        mapProps.put(fieldName, singletonList(fieldValue));
        return getByProps(mapProps);
    }

    protected CriteriaQuery<T> getCriteriaByProps(Map<String, List<?>> props) {
        Objects.requireNonNull(props, "Param 'props' cannot be null, sorry");

        CriteriaQuery<T> criteria = getCriteriaQuery();
        Root<T> root = getRoot(criteria);
        criteria.select(root);

        return getCriteriaByPropsByRoot(props, criteria, root);
    }

    private CriteriaQuery<T> getCriteriaByPropsByRoot(Map<String, List<?>> props, CriteriaQuery<T> criteria, Root<T> root) {
        List<Predicate> predicates = props.entrySet().stream()
                .map(e -> getPredicateInByFieldNameAndValues(root, e.getKey(), e.getValue()))
                .collect(toList());

        criteria.where(getCriteriaBuilder().and(predicates.toArray(new Predicate[predicates.size()])));
        return criteria;
    }

    protected Predicate getPredicateInByFieldNameAndValues(Root<T> root, String fieldName, List<?> values) {
        if (values.isEmpty()) return null;

        if (EntityFieldUtils.isRelationField(fieldName)) {
            return getPredicateInRelationField(root, fieldName, values);
        }

        Class<?> fieldType = ReflectionUtils.getFieldType(getPersistentClass(), fieldName);
        return getPredicateInByValuesWithCast(root, fieldName, values, fieldType);
    }

    private Predicate getPredicateInRelationField(Root<T> root, String fieldName, List<?> values) {
        String relationFieldAlias = EntityFieldUtils.getRelationFieldAlias(fieldName);
        String relationFieldName = EntityFieldUtils.getRelationFieldName(fieldName);

        Class<?> relationFieldClass = ReflectionUtils.getFieldType(getPersistentClass(), relationFieldAlias);
        Class<?> relationFieldType = ReflectionUtils.getFieldType(relationFieldClass, relationFieldName);

        Join<T, ?> join = root.join(relationFieldAlias);

        return getPredicateInByValuesByJoinWithCast(join, relationFieldName, values, relationFieldType);
    }

    private Predicate getPredicateInByValuesByJoinWithCast(Join<T, ?> join, String fieldName, List<?> values, Class<?> fieldType) {
        Set<Object> castedValues = getCastedValues(values, fieldType);
        Path<Object> expression = join.get(fieldName);
        return getPredicateIn(castedValues, expression);
    }

    private Predicate getPredicateInByValuesWithCast(Root<T> root, String fieldName, List<?> values, Class<?> fieldType) {
        boolean isExistNullValue = values.stream()
                .anyMatch(Objects::isNull);

        if (isExistNullValue) {
            Path<Object> expression = root.get(fieldName);
            return expression.isNull();
        } else {
            Set<Object> castedValues = getCastedValues(values, fieldType);
            Path<Object> expression = root.get(fieldName);
            return getPredicateIn(castedValues, expression);
        }
    }

    private Predicate getPredicateIn(Set<Object> castedValues, Path<Object> expression) {
        return expression.in(castedValues);
    }

    private Set<Object> getCastedValues(List<?> values, Class<?> fieldType) {
        return values.stream()
                .map(v -> ReflectionUtils.castFieldValueByType(fieldType, v))
                .collect(toSet());
    }

    @Override
    public List<T> getByFields(Collection<FieldHolder> fieldHolders) {
        Objects.requireNonNull(fieldHolders, "Param 'fieldHolders' cannot be null, sorry");

        if (isEmpty(fieldHolders)) {
            return emptyList();
        }

        CriteriaQuery<T> criteria = getCriteriaQuery();
        Root<T> root = getRoot(criteria);
        criteria.select(root);

        List<Predicate> predicates = fieldHolders.stream()
                .map(f -> getPredicateEqByFieldHolder(root, f))
                .collect(toList());

        criteria.where(getCriteriaBuilder().and(predicates.toArray(new Predicate[predicates.size()])));
        return getSession().createQuery(criteria).getResultList();
    }

    private Predicate getPredicateEqByFieldHolder(Root<T> root, FieldHolder fieldHolder) {
        Assert.notNull(fieldHolder.getName(), "Field name cannot be null");

        if (isEmpty(fieldHolder.getRelationFieldName())) {
            return getPredicateEqByFieldWithCast(root, fieldHolder);
        }

        return getPredicateEqByRelationField(root, fieldHolder);
    }

    private Predicate getPredicateEqByFieldWithCast(Root<T> root, FieldHolder fieldHolder) {
        return getPredicateEqByFieldWithCast(root, fieldHolder.getName(), fieldHolder.getValue());
    }

    protected Predicate getPredicateEqByFieldWithCast(Root<T> root, String fieldName, Object fieldValue) {
        Object fieldValueCasted = getCastedValue(fieldName, fieldValue);
        return getPredicateEqByField(root, fieldName, fieldValueCasted);
    }

    private Object getCastedValue(String fieldName, Object fieldValue) {
        if (fieldValue == null) {
            return null;
        }

        return ReflectionUtils.castFieldValueByClass(getPersistentClass(), fieldName, fieldValue);
    }

    protected Predicate getPredicateEqByRelationField(Root<T> root, String fieldName, Object fieldValue, String relationFieldName) {
        Class<?> relationFieldClass = ReflectionUtils.getFieldType(getPersistentClass(), relationFieldName);
        Object valueFromRelationObject = ReflectionUtils.castFieldValueByClass(relationFieldClass, fieldName, fieldValue);

        Join<T, ?> join = root.join(relationFieldName);

        return getPredicateEqByFieldByJoin(join, fieldName, valueFromRelationObject);
    }

    /**
     * Important! equals without cast type - do the cast yourself
     */
    protected Predicate getPredicateEqByFieldByJoin(Join<T, ?> join, String fieldName, Object fieldValue) {
        Path<Object> path = join.get(fieldName);
        return getCriteriaBuilder().equal(path, fieldValue);
    }

    /**
     * Important! equals without cast type - do the cast yourself
     */
    protected Predicate getPredicateEqByField(Root<T> root, String fieldName, Object fieldValue) {
        Path<Object> path = root.get(fieldName);
        return getCriteriaBuilder().equal(path, fieldValue);
    }

    private Predicate getPredicateEqByRelationField(Root<T> root, FieldHolder fieldHolder) {
        return getPredicateEqByRelationField(root, fieldHolder.getName(), fieldHolder.getValue(), fieldHolder.getRelationFieldName());
    }

    @Override
    public List<T> universalQuery(Map<String, List<?>> fields, QueryParams queryParams) {
        Objects.requireNonNull(fields, "Param 'props' cannot be null, sorry");

        CriteriaQuery<T> criteria = getCriteriaQuery();
        Root<T> root = getRoot(criteria);
        criteria.select(root);

        CriteriaQuery<T> criteriaByProps = getCriteriaByPropsByRoot(fields, criteria, root);

        Query<T> queryByQueryParams = getQueryByQueryParams(root, criteriaByProps, queryParams);

        return queryByQueryParams.getResultList();
    }

    private Query<T> getQueryByQueryParams(Root<T> root, CriteriaQuery<T> criteria, QueryParams queryParams) {
        if (queryParams.getSortBy() != null) {
            Path<Object> path = root.get(queryParams.getSortBy());
            if (queryParams.getOrderType() == null || queryParams.getOrderType().equals(OrderType.ASC)) {
                criteria.orderBy(getCriteriaBuilder().asc(path));
            } else if (queryParams.getOrderType().equals(OrderType.DESC)) {
                criteria.orderBy(getCriteriaBuilder().desc(path));
            }
        }

        Query<T> query = getSession().createQuery(criteria);

        if (queryParams.getStart() != null)
            query.setFirstResult(queryParams.getStart());

        if (queryParams.getLimit() != null)
            query.setMaxResults(queryParams.getLimit());

        return query;
    }

    @Override
    public void updateMultiple(String fieldName, Map<?, ?> mapOldNewValue) {
        Objects.requireNonNull(fieldName, "Param 'fieldName' cannot be null");

        if (isEmpty(mapOldNewValue)) {
            return;
        }

        String sqlQueryIn = getSqlQueryInFromList(mapOldNewValue.keySet());

        StringBuilder builder = new StringBuilder();

        builder.append(" update ");
        builder.append(getPersistentClass().getSimpleName());
        builder.append(" set ");
        builder.append(escapeValue(fieldName));
        builder.append(" = ");
        builder.append(" case ");
        builder.append(escapeValue(fieldName));
        builder.append(" ");
        for (Map.Entry<?, ?> entry : mapOldNewValue.entrySet()) {
            builder.append(" when ");
            builder.append(":v_old" + convertToParamName(entry.getKey()));
            builder.append(" then ");
            builder.append(":v_new" + convertToParamName(entry.getValue()));
            builder.append(" ");
        }
        builder.append(" end ");
        builder.append(" where ");
        builder.append(escapeValue(fieldName));
        builder.append(sqlQueryIn);

        String queryString = builder.toString();
        Query query = getSession().createQuery(queryString);

        for (Map.Entry<?, ?> entry : mapOldNewValue.entrySet()) {
            query.setParameter("v_old" + convertToParamName(entry.getKey()), entry.getKey());
            query.setParameter("v_new" + convertToParamName(entry.getValue()), entry.getValue());
        }

        query.executeUpdate();
    }

    protected String convertToParamName(Object object) {
        if (object instanceof Number) {
            return object.toString();
        }

        return object.toString().replaceAll("\\s+", "_");
    }

    protected String getSqlQueryInFromList(Collection<?> values) {
        String str = values.stream()
                .map(val -> ":v_old" + convertToParamName(val))
                .collect(joining(", ", "(", ")"));
        return " in " + str;
    }

    private Object escapeValue(Object val) {
        if (val instanceof Number) {
            return val;
        }

        return escapeString((val.toString()), true);
    }
}
