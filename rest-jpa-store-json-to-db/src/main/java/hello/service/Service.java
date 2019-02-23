package hello.service;

import hello.entity.BaseEntity;

public interface Service<T extends BaseEntity<ID>, ID> {

    Iterable<T> getAll();

    T getById(ID id);

    T update(ID id, T entity);

    T save(T entity);

    void delete(T entity);

    void delete(ID id);
}
