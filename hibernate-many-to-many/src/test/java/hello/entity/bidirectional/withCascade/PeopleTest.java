package hello.entity.bidirectional.withCascade;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import org.junit.Test;

import java.util.Set;

import static java.util.Collections.singleton;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@DatabaseSetup("/people_country.xml")
public class PeopleTest extends AbstractJpaTest {

    @Test
    public void getCountryFromPeople() {
        People people = entityManager.find(People.class, 1L);

        Set<Country> countries = people.getCountries();

        assertThat(countries.size(), equalTo(2));
        assertThat(countries, containsInAnyOrder(
                hasProperty("id", is(1L)),
                hasProperty("id", is(2L))
        ));
    }

    @Test
    public void createPeopleWithCountry_WhenCountryNotSaved_WhenSetToPeople_ShouldRelationCreated() {
        People people = new People("NewPeople");
        Country country = new Country("NewCountry");

        people.setCountries(singleton(country));  // owner

        People peopleFromDb = entityManager.persistFlushFind(people);
        assertThat(peopleFromDb.getCountries().size(), equalTo(1));

    }

    @Test
    public void createPeopleWithCountry_WhenCountryNotSaved_WhenSetToPeopleAndCountry_ShouldRelationCreated() {
        People people = new People("NewPeople");
        Country country = new Country("NewCountry");

        people.setCountries(singleton(country));  // owner
        country.setPeoples(singleton(people));

        People peopleFromDb = entityManager.persistFlushFind(people);
        assertThat(peopleFromDb.getCountries().size(), equalTo(1));
    }

    @Test
    public void createPeopleWithCountry_WhenCountrySaved_WhenSetToPeople_ShouldRelationCreated() {
        People people = new People("NewPeople");
        Country country = new Country("NewCountry");
        entityManager.persistAndFlush(country);

        people.setCountries(singleton(country));  // owner

        People peopleFromDb = entityManager.persistFlushFind(people);
        assertThat(peopleFromDb.getCountries().size(), equalTo(1));
    }

    @Test
    public void createPeopleWithCountry_WhenCountrySaved_WhenSetToCountry_ShouldRelationNotCreated() {
        People people = new People("NewPeople");
        Country country = new Country("NewCountry");
        entityManager.persistAndFlush(country);

        country.setPeoples(singleton(people));

        People peopleFromDb = entityManager.persistFlushFind(people);
        assertThat(peopleFromDb.getCountries().size(), equalTo(0));
    }

    @Test
    public void createPeopleWithCountry_WhenCountrySaved_WhenSetToPeopleAndCountry_ShouldRelationCreated() {
        People people = new People("NewPeople");
        Country country = new Country("NewCountry");
        entityManager.persistAndFlush(country);

        people.setCountries(singleton(country));  // owner
        country.setPeoples(singleton(people));

        People peopleFromDb = entityManager.persistFlushFind(people);
        assertThat(peopleFromDb.getCountries().size(), equalTo(1));
    }

    @Test
    public void deletePeople_ShouldRelationObjectDeleted() {
        People people = entityManager.find(People.class, 1L);
        entityManager.remove(people);
        flushAndClean();

        // check than countries deleted
        Country country1 = entityManager.find(Country.class, 1L);
        assertThat(country1, equalTo(null));
        Country country2 = entityManager.find(Country.class, 2L);
        assertThat(country2, equalTo(null));
    }
}