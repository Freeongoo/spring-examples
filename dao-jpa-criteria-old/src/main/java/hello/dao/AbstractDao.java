package hello.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface AbstractDao<T, ID extends Serializable> {

    public List<T> getAll();

    public Optional<T> getById(ID id);

    public void persist(T entity);

    public void saveOrUpdate(T entity);

    public void delete(T entity);
}
