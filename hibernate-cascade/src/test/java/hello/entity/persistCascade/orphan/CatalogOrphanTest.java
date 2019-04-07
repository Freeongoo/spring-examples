package hello.entity.persistCascade.orphan;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.persistence.PersistenceException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * When `mappedBy = "client", orphanRemoval = true` - nothing difference when only
 * `mappedBy = "client", cascade = CascadeType.REMOVE`
 * except for the situation when we can not directly pass to the null collection
 */
@DatabaseSetup("/persistCascade/orphan/catalog_good_orphan.xml")
public class CatalogOrphanTest extends AbstractJpaTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void deleteCatalogOrphan() {
        CatalogOrphan catalog = entityManager.find(CatalogOrphan.class, 1L);

        entityManager.remove(catalog);

        flushAndClean();

        GoodOrphan goodOrphan1 = entityManager.find(GoodOrphan.class, 1L);
        assertThat(goodOrphan1, equalTo(null));

        GoodOrphan goodOrphan2 = entityManager.find(GoodOrphan.class, 2L);
        assertThat(goodOrphan2, equalTo(null));
    }

    @Test
    public void tryDeleteCatalogOrphanDependenciesByCleanOnlyCollections_WhenSetNullCollections_ShouldThrowException() {
        this.thrown.expect(PersistenceException.class);
        this.thrown.expectMessage("org.hibernate.HibernateException: A collection with cascade=\"all-delete-orphan\" was no longer referenced by the owning entity instance: hello.entity.persistCascade.orphan.CatalogOrphan.goodOrphans");

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

        flushAndClean();

        CatalogOrphan catalogAfterCleanCollection2 = entityManager.find(CatalogOrphan.class, 1L);
        assertThat(catalogAfterCleanCollection2.getGoodOrphans().size(), equalTo(0)); // removed
    }
}