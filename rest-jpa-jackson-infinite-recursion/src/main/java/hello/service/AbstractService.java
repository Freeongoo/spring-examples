package hello.service;

import hello.exception.ErrorCode;
import hello.exception.NotFoundException;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public abstract class AbstractService<T, ID> implements Service<T, ID> {

    protected abstract CrudRepository<T, ID> getRepository();

    @Override
    public T getById(ID id) {
        return getRepository().findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Cannot find entity by id: " + id, ErrorCode.OBJECT_NOT_FOUND)
                );
    }

    @Override
    public List<T> getAll() {
        return (List<T>) getRepository().findAll();
    }
}
