package example.dao;

import java.io.Serializable;
import java.util.List;

public interface Dao<PK extends Serializable,T> {
	List <T> getAll();
	T getByKey(PK key);
	void persist(T entity);
	void saveOrUpdate(T entity);
	void delete(T entity);
	void flush();
}
