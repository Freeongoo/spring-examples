package hello.service.impl;

import hello.entity.oneToMany.BaseEntity;
import hello.exception.ErrorCode;
import hello.exception.ResourceNotFoundException;
import hello.service.Service;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public abstract class AbstractService<T extends BaseEntity<ID>, ID> implements Service<T, ID> {

    protected abstract CrudRepository<T, ID> getRepository();

    @Override
    public Iterable<T> getAll() {
        return getRepository().findAll();
    }

    @Override
    public T getById(ID id) {
        return getRepository().findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Cannot find entity by id: " + id, ErrorCode.OBJECT_NOT_FOUND)
                );
    }

    @Override
    public T update(ID id, T entity) {
        Optional<T> itemFromDb = getRepository().findById(id);
        return itemFromDb
                .map(c -> {
                    entity.setId(c.getId());
                    return getRepository().save(entity);
                })
                .orElseThrow(
                        () -> new ResourceNotFoundException("Cannot update - not exist entity by id: " + id, ErrorCode.OBJECT_NOT_FOUND)
                );
    }

    @Override
    public T save(T entity) {
        return getRepository().save(entity);
    }

    @Override
    public void delete(T entity) {
        getRepository().delete(entity);
    }

    @Override
    public void delete(ID id) {
        getRepository().deleteById(id);
    }
}
