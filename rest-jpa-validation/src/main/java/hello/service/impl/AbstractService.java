package hello.service.impl;

import hello.entity.BaseEntity;
import hello.exception.ErrorCode;
import hello.exception.NotValidParamsException;
import hello.exception.ResourceNotFoundException;
import hello.service.Service;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.function.Supplier;

import static hello.exception.ErrorCode.INVALID_PARAMS;
import static hello.exception.ErrorCode.OBJECT_NOT_FOUND;

public abstract class AbstractService<T extends BaseEntity> implements Service<T, Long> {

    protected abstract CrudRepository<T, Long> getRepository();

    @Override
    public Iterable<T> getAll() {
        return getRepository().findAll();
    }

    @Override
    public T getById(Long id) {
        return getRepository()
                .findById(id)
                .orElseThrow(getNotFoundExceptionSupplier("Cannot find entity by id: " + id, OBJECT_NOT_FOUND));
    }

    @Override
    public T update(Long id, T entity) {
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
        Long id = entity.getId();
        if (id == null) {
            throw new NotValidParamsException("Try delete entity without id", INVALID_PARAMS);
        }

        validateExistingEntityById(id);

        getRepository().delete(entity);
    }

    @Override
    public void delete(Long id) {
        validateExistingEntityById(id);

        getRepository().deleteById(id);
    }

    private void validateExistingEntityById(Long id) {
        getRepository()
                .findById(id)
                .orElseThrow(getNotFoundExceptionSupplier("Cannot find entity by id: " + id, OBJECT_NOT_FOUND));
    }

    protected Supplier<ResourceNotFoundException> getNotFoundExceptionSupplier(String message, ErrorCode code) {
        return () -> new ResourceNotFoundException(message, code);
    }
}
