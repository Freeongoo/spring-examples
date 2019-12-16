package hello.entity.cache;

import hello.entity.AbstractBaseEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "good_read_only")
@Cacheable
@org.hibernate.annotations.Cache(region = "referenceCache", usage = CacheConcurrencyStrategy.READ_ONLY)
public class GoodReadOnly extends AbstractBaseEntity<Long> {
}
