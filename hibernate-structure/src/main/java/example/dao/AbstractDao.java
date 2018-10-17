package example.dao;

import org.hibernate.Criteria;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Transactional
public abstract class AbstractDao<PK extends Serializable, T> implements Dao<PK, T> {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

	protected abstract Class<T> getPersistentClass();

	protected Criteria createEntityCriteria() {
		return getSession().createCriteria(getPersistentClass()).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return getSession().createCriteria(getPersistentClass()).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	protected Session getSession() {
		return entityManagerFactory.unwrap(SessionFactory.class).getCurrentSession();
	}

	public T getByKey(PK key) {
		return getSession().get(getPersistentClass(), key);
	}

	public T loadByKey(PK key) {
		return getSession().load(getPersistentClass(), key);
	}

	public void persist(T entity) {
		getSession().persist(entity);
	}

	public void replicate(T entity) {
		getSession().replicate(entity, ReplicationMode.OVERWRITE);
	}

	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
	}

	public void delete(T entity) {
		getSession().delete(entity);
	}

	public void flush() {
		getSession().flush();
		getSession().clear();
	}
}
