package hello.service;

import hello.exception.ErrorCode;
import hello.exception.NotFoundException;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T, ID> implements BaseService<T, ID> {

    protected abstract CrudRepository<T, ID> getRepository();

    @Override
    public T getById(ID id) {
        Optional<T> product = getRepository().findById(id);
        return product
                .orElseThrow(() -> new NotFoundException("Cannot find entity by id: " + id, ErrorCode.OBJECT_NOT_FOUND));
    }

    @Override
    public List<T> getAll() {
        return (List<T>) getRepository().findAll();
    }
}
