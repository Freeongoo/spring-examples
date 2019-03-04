package hello;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Abstract class for common methods for test equals/hashCode entities
 * @param <T> Entity
 */
public abstract class AbstractHibernateCheckEqualsHashCodeTest<T> extends AbstractHibernateTest {

    /**
     * Important passed transient entity
     * @param entity transient entity
     */
    protected void checkTheContentsOfTheSetUnderDifferentStatesOfTheEntity(T entity) {
        flushAndClean();

        Set<T> map = new HashSet<>();

        // smoke check
        assertFalse(map.contains(entity));

        map.add(entity);
        assertTrue("The entity is not found in the Set after added transient entity.", map.contains(entity));

        em.persist(entity);
        flushAndClean();
        assertTrue("The entity is not found in the Set after it's persisted.", map.contains(entity));

        T itemMerged = em.merge(entity);
        flushAndClean();
        assertTrue("The entity is not found in the Set after it's merged.", map.contains(itemMerged));

        em.detach(entity);
        flushAndClean();
        assertTrue("The entity is not found in the Set after it's detached.", map.contains(entity));

        T itemMergedAgain = em.merge(entity);
        flushAndClean();
        assertTrue("The entity is not found in the Set after it's reattached.", map.contains(itemMergedAgain));
    }
}
