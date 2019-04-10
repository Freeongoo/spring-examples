package hello.entity.orphanRemoval;

import hello.AbstractJpaTest;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class PersonTest extends AbstractJpaTest {

    @Test
    public void removeOneFromList() {
        Person person = new Person();
        Phone phone1 = new Phone("123-456-7890");
        Phone phone2 = new Phone("321-654-0987");

        person.addPhone(phone1);
        person.addPhone(phone2);

        entityManager.persist(person);
        entityManager.flush();

        person.removePhone(phone1);

        flushAndClean();

        Phone phoneFromDb = entityManager.find(Phone.class, phone1.getId());
        assertThat(phoneFromDb, equalTo(null));   // removed because orphanRemoval = true
                                                          // (if orphanRemoval = false) only remove relation
    }
}