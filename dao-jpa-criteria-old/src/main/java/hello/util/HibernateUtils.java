package hello.util;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

public final class HibernateUtils {

    private static final String IDENTIFIER_FIELD_NAME = "id";

    private HibernateUtils() { }

    /**
     * Get identifier of entity.
     * The advantage of this method is that it allows you to get an identifier from a proxy object
     *
     * @param entity entity
     * @return identifier
     */
    public static Object getIdFromEntity(final Object entity) {
        return getIdFromEntity(entity, IDENTIFIER_FIELD_NAME);
    }

    /**
     * Get identifier of entity with passed name of id
     * The advantage of this method is that it allows you to get an identifier from a proxy object
     *
     * @param entity entity
     * @param identifierFieldName name of id
     * @return identifier
     */
    public static Object getIdFromEntity(final Object entity, String identifierFieldName) {
        Object id;
        if (entity instanceof HibernateProxy) {
            id = ((HibernateProxy)entity).getHibernateLazyInitializer().getIdentifier();
        } else {
            id = ReflectionUtils.getFieldContent(entity, identifierFieldName);
        }

        return ReflectionUtils.castFieldValue(entity.getClass(), identifierFieldName, id);
    }

    /**
     * Convert proxy hibernate object - to real object
     *
     * @param entity entity, may be
     * @param <T> T
     * @return T
     */
    public static <T> T initializeAndUnproxy(T entity) {
        if (entity == null) {
            throw new NullPointerException("Entity passed for initialization is null");
        }

        Hibernate.initialize(entity);
        if (entity instanceof HibernateProxy) {
            entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer()
                    .getImplementation();
        }
        return entity;
    }
}
