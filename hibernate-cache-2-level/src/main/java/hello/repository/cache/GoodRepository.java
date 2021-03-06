package hello.repository.cache;

import hello.entity.cache.GoodReadOnly;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface GoodRepository extends JpaRepository<GoodReadOnly, Long> {

    Collection<GoodReadOnly> findByName(String name);

    @Cacheable(value="movieFindCache", key="#name")
    Collection<GoodReadOnly> findByNameByCache(String name);
}
