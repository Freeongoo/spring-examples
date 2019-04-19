package hello.entity.bidirectional.typeOfCollection.Collection;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import hello.sqltracker.AssertSqlCount;
import org.junit.Test;

@DatabaseSetup("/glass_holder.xml")
public class GlassTest extends AbstractJpaTest {

    @Test
    public void deleteOneHolderFromGlass() {
        Glass glass = entityManager.find(Glass.class, 1L);
        GlassHolder glassHolder = entityManager.find(GlassHolder.class, 1L);
        glass.getGlassHolders().remove(glassHolder);

        flushAndClean();

        AssertSqlCount.assertSelectCount(3);
        AssertSqlCount.assertDeleteCount(1);
        AssertSqlCount.assertInsertCount(1);    // request not needed, executed because collection type Collection (use Set)
        AssertSqlCount.assertUpdateCount(0);
    }
}