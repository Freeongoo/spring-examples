package hello.service.impl;

import hello.entity.BaseEntity;
import hello.exception.ErrorCode;
import hello.exception.ResourceNotFoundException;
import hello.service.Service;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.function.Supplier;

import static hello.exception.ErrorCode.OBJECT_NOT_FOUND;

public abstract class AbstractService<T extends BaseEntity<ID>, ID> implements Service<T, ID> {

    protected abstract CrudRepository<T, ID> getRepository();

    @Override
    public Iterable<T> getAll() {
        return getRepository().findAll();
    }

    @Override
    public T getById(ID id) {
        return getRepository()
                .findById(id)
                .orElseThrow(getNotFoundExceptionSupplier("Cannot find entity by id: " + id, OBJECT_NOT_FOUND));
    }

    @Override
    public T update(ID id, T entity) {
        Optional<T> optionalEntityFromDB = getRepository().findById(id);
        return optionalEntityFromDB
                .map(e -> saveAndReturnSavedEntity(entity, e))
                .orElseThrow(getNotFoundExceptionSupplier("Cannot update - not exist entity by id: " + id, OBJECT_NOT_FOUND));
    }

    private T saveAndReturnSavedEntity(T entity, T entityFromDB) {
        entity.setId(entityFromDB.getId());
        return getRepository().save(entity);
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
        getRepository()
                .findById(id)
                .orElseThrow(getNotFoundExceptionSupplier("Cannot find entity by id: " + id, OBJECT_NOT_FOUND));

        getRepository().deleteById(id);
    }

    protected Supplier<ResourceNotFoundException> getNotFoundExceptionSupplier(String message, ErrorCode code) {
        return () -> new ResourceNotFoundException(message, code);
    }
}
