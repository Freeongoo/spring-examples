package example.dao;

import example.entity.BaseEntity;

import javax.transaction.Transactional;
import java.io.Serializable;

@Transactional
public abstract class AbstractBaseEntityDao<PK extends Serializable, T extends BaseEntity> extends AbstractDao<PK, T> {

	@Override
	public void persist(T entity) {
		entity.prepare();
		super.persist(entity);
	}

	@Override
	public void replicate(T entity) {
		entity.prepare();		
		super.replicate(entity);
	}

	@Override
	public void saveOrUpdate(T entity) {
		entity.prepare();
		super.saveOrUpdate(entity);
	}
}
