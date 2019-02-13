package hello.service;

import hello.entity.BaseEntity;
import hello.exception.ErrorCode;
import hello.exception.ResourceNotFoundException;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

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
                .orElseThrow(getResourceNotFoundExceptionSupplier("Cannot find entity by id: " + id));
    }

    @Override
    public T update(ID id, T entity) {
        Optional<T> optionalItem = getRepository().findById(id);
        return optionalItem
                .map(setIdAndSave(entity))
                .orElseThrow(getResourceNotFoundExceptionSupplier("Cannot update - not exist entity by id: " + id));
    }

    private Function<T, T> setIdAndSave(T entity) {
        return item -> {
            entity.setId(item.getId());
            return getRepository().save(entity);
        };
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

    private Supplier<ResourceNotFoundException> getResourceNotFoundExceptionSupplier(String message) {
        return () -> new ResourceNotFoundException(message, ErrorCode.OBJECT_NOT_FOUND);
    }
}
