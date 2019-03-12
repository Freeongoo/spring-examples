package hello.dao;

import hello.container.FieldHolder;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BaseDao<T, ID extends Serializable> {

    public List<T> getAll();

    public Optional<T> getById(ID id);

    public void persist(T entity);

    public void saveOrUpdate(T entity);

    public void delete(T entity);

    /**
     * When passed empty map or all not exist fields - return all
     * When passed existing fields by not exist values - return empty list
     *
     * @param props props
     * @return list of entities
     */
    public List<T> getByProps(Map<String, List<?>> props);

    /**
     * When passed empty fieldHolders - return empty list
     * When passed all not exist fields name - return empty list
     *
     * @param fieldHolders fieldHolders
     * @return list of entities
     */
    public List<T> getByFields(Collection<FieldHolder> fieldHolders);
}
