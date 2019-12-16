package hello.entity.cache;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import hello.repository.cache.GoodRepository;
import hello.sqltracker.AssertSqlCount;
import org.hibernate.Cache;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@DatabaseSetup({"/good-read-only.xml"})
public class GoodReadOnlyTest extends AbstractJpaTest {

    private Cache secondLevelCache;

    @Autowired
    private GoodRepository goodRepository;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        // clear second level cache for correct test
        secondLevelCache = session.getSessionFactory().getCache();
        secondLevelCache.evictEntityData(GoodReadOnly.class, 1L);
    }

    @Test
    public void findById_WhenCheckSetEntityToCache() {
        assertThat(secondLevelCache.containsEntity(GoodReadOnly.class, 1L), equalTo(false));

        Optional<GoodReadOnly> goodOptional = goodRepository.findById(1L);
        assertTrue(goodOptional.isPresent());

        assertThat(secondLevelCache.containsEntity(GoodReadOnly.class, 1L), equalTo(true));
    }

    @Test
    public void findById_WhenMultiplyGetByIdByJpaRepository_ShouldBeOneSelectRequest() {
        Cache secondLevelCache = session.getSessionFactory().getCache();
        secondLevelCache.evictEntityData(GoodReadOnly.class, 1L);

        Optional<GoodReadOnly> goodOptional = goodRepository.findById(1L);
        assertTrue(goodOptional.isPresent());

        flushAndClean();

        Optional<GoodReadOnly> goodOptionalAgain = goodRepository.findById(1L);
        assertTrue(goodOptionalAgain.isPresent());

        flushAndClean();

        Optional<GoodReadOnly> goodOptionalAgainAgain = goodRepository.findById(1L);
        assertTrue(goodOptionalAgainAgain.isPresent());

        AssertSqlCount.assertSelectCount(1);
    }

    @Test
    public void findById_WhenMultiplyGetByIdByEntityManager_ShouldBeOneSelectRequest() {
        Cache secondLevelCache = session.getSessionFactory().getCache();
        secondLevelCache.evictEntityData(GoodReadOnly.class, 1L);

        GoodReadOnly good = entityManager.find(GoodReadOnly.class, 1L);

        flushAndClean();

        GoodReadOnly good2 = entityManager.find(GoodReadOnly.class, 1L);

        AssertSqlCount.assertSelectCount(1);
    }
}