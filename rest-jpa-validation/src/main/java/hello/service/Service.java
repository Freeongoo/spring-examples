package hello.service;

import hello.entity.BaseEntity;
import hello.exception.ResourceNotFoundException;

public interface Service<T extends BaseEntity, ID> {

    /**
     * Get all entities, if not exist return empty collection
     *
     * @return collection
     */
    Iterable<T> getAll();

    /**
     * Get entity by id, if not exist throw exception {@link ResourceNotFoundException}
     *
     * @param id id
     * @return entity
     */
    T getById(ID id);

    /**
     * Update passed entity from id by passed entity.
     * If not exist entity by id throw exception {@link ResourceNotFoundException}
     *
     * @param id id
     * @param entity entity
     * @return updated entity
     */
    T update(ID id, T entity);

    /**
     * Create new entity
     *
     * @param entity entity
     * @return saved entity
     */
    T save(T entity);

    /**
     * Delete by entity
     *
     * @param entity entity
     */
    void delete(T entity);

    /**
     * Delete by id, if not exist entity by id throw exception {@link ResourceNotFoundException}
     *
     * @param id id
     */
    void delete(ID id);
}
