package hello.entity.orphanRemoval;

import hello.AbstractJpaTest;
import org.junit.Test;

import javax.persistence.PersistenceException;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
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

    @Test
    public void removeOneFromList_WhenRemoveOnlyFromAuthorSide_ShouldBeDeleted() {
        Person person = new Person();
        Phone phone1 = new Phone("123-456-7890");
        Phone phone2 = new Phone("321-654-0987");

        person.addPhone(phone1);
        person.addPhone(phone2);

        entityManager.persist(person);
        entityManager.flush();

        person.getPhones().remove(phone1);

        flushAndClean();

        Phone phoneFromDb = entityManager.find(Phone.class, phone1.getId());
        assertThat(phoneFromDb, equalTo(null));

        Person personFromDb = entityManager.find(Person.class, person.getId());
        assertThat(personFromDb.getPhones().size(), equalTo(1));
    }

    @Test
    public void remove_WhenRemoveAllPhones_ShouldBeDeleted() {
        Person person = new Person();
        Phone phone1 = new Phone("123-456-7890");
        Phone phone2 = new Phone("321-654-0987");

        person.addPhone(phone1);
        person.addPhone(phone2);

        entityManager.persist(person);
        entityManager.flush();

        person.getPhones().clear();

        flushAndClean();

        Phone phoneFromDb = entityManager.find(Phone.class, phone1.getId());
        assertThat(phoneFromDb, equalTo(null));
        Phone phoneFromDb2 = entityManager.find(Phone.class, phone2.getId());
        assertThat(phoneFromDb2, equalTo(null));

        Person personFromDb = entityManager.find(Person.class, person.getId());
        assertThat(personFromDb.getPhones().size(), equalTo(0));
    }

    @Test(expected = PersistenceException.class)
    public void remove_WhenWrongRemoveAllPhones_ShouldBeThrowException() {
        Person person = new Person();
        Phone phone1 = new Phone("123-456-7890");
        Phone phone2 = new Phone("321-654-0987");

        person.addPhone(phone1);
        person.addPhone(phone2);

        entityManager.persist(person);
        entityManager.flush();

        person.setPhones(null); // don't do this --> use: person.getPhones().clear();

        flushAndClean();
    }
}