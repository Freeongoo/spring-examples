package hello.dao;

import hello.container.FieldHolder;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AbstractDao<T, ID extends Serializable> {

    public List<T> getAll();

    public Optional<T> getById(ID id);

    public void persist(T entity);

    public void saveOrUpdate(T entity);

    public void delete(T entity);

    public List<T> getByProps(Map<String, List<?>> props);

    public List<T> getByFields(Collection<FieldHolder> fieldHolders);
}
