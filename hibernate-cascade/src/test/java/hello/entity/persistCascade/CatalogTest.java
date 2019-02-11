package hello.entity.persistCascade;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.persistence.PersistenceException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

@DatabaseSetup("/persistCascade/catalog_good.xml")
public class CatalogTest extends AbstractJpaTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void deleteCatalog_ShouldThrowException() {
        this.thrown.expect(PersistenceException.class);
        this.thrown.expectMessage("org.hibernate.exception.ConstraintViolationException: could not execute statement");

        Catalog catalog = entityManager.find(Catalog.class, 1L);

        entityManager.remove(catalog);

        flushAndClean();
    }

    @Test
    public void tryDeleteCatalogDependenciesByCleanOnlyCollections_WhenSetNullCollections() {
        Catalog catalog = entityManager.find(Catalog.class, 1L);

        catalog.setGoods(null);
        entityManager.persist(catalog);

        flushAndClean();

        Catalog catalogAfterCleanCollection = entityManager.find(Catalog.class, 1L);
        assertThat(catalogAfterCleanCollection.getGoods().size(), equalTo(2)); // nothing deleted
    }

    @Test
    public void tryDeleteCatalogDependenciesByCleanOnlyCollections_WhenCleanCollections() {
        Catalog catalog = entityManager.find(Catalog.class, 1L);

        catalog.getGoods().clear();
        entityManager.persist(catalog);

        flushAndClean();

        Catalog catalogAfterCleanCollection2 = entityManager.find(Catalog.class, 1L);
        assertThat(catalogAfterCleanCollection2.getGoods().size(), equalTo(2)); // nothing deleted
    }
}