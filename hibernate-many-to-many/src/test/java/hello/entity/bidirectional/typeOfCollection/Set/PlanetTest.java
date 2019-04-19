package hello.entity.bidirectional.typeOfCollection.Set;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import hello.sqltracker.AssertSqlCount;
import org.junit.Test;

@DatabaseSetup("/planet_human.xml")
public class PlanetTest extends AbstractJpaTest {

    @Test
    public void deleteOneHumanFromPlanet() {
        Planet planet = entityManager.find(Planet.class, 1L);
        Human human = entityManager.find(Human.class, 1L);
        planet.getHumans().remove(human);

        flushAndClean();

        AssertSqlCount.assertSelectCount(3);
        AssertSqlCount.assertDeleteCount(1);
        AssertSqlCount.assertInsertCount(0);
        AssertSqlCount.assertUpdateCount(0);
    }
}