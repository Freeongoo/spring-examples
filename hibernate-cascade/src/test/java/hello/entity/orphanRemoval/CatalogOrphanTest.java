package hello.entity.orphanRemoval;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.persistence.PersistenceException;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@DatabaseSetup("/persistCascade/orphan/catalog_good_orphan.xml")
public class CatalogOrphanTest extends AbstractJpaTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void tryDeleteCatalogOrphanDependenciesByCleanOnlyCollections_WhenSetNullCollections_ShouldThrowException() {
        this.thrown.expect(PersistenceException.class);

        CatalogOrphan catalog = entityManager.find(CatalogOrphan.class, 1L);

        catalog.setGoodOrphans(null); // cannot do this if orphanRemoval = true
        entityManager.persist(catalog);

        flushAndClean();
    }

    @Test
    public void tryDeleteCatalogOrphanDependenciesByCleanOnlyCollections_WhenCleanCollections() {
        CatalogOrphan catalog = entityManager.find(CatalogOrphan.class, 1L);

        catalog.getGoodOrphans().clear();
        entityManager.persist(catalog);

        entityManager.flush();
        entityManager.clear();

        CatalogOrphan catalogAfterCleanCollection2 = entityManager.find(CatalogOrphan.class, 1L);
        assertThat(catalogAfterCleanCollection2.getGoodOrphans().size(), equalTo(0)); // removed relations
                                                                                              // only from catalog

        GoodOrphan goodOrphan = entityManager.find(GoodOrphan.class, 1L);
        assertThat(goodOrphan.getCatalogOrphan(), is(notNullValue()));
    }
}