package hello.entity.bidirectional.withCascade;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import org.junit.Test;

import java.util.Set;

import static java.util.Collections.singleton;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@DatabaseSetup("/people_country.xml")
public class CountryTest extends AbstractJpaTest {

    @Test
    public void getPeopleFromCountry() {
        Country country = entityManager.find(Country.class, 1L);

        Set<People> peoples = country.getPeoples();

        assertThat(peoples.size(), equalTo(2));
        assertThat(peoples, containsInAnyOrder(
                hasProperty("id", is(1L)),
                hasProperty("id", is(2L))
        ));
    }

    @Test
    public void createCountryWithPeople_WhenPeopleNotSaved_WhenSetToCountry_ShouldRelationNotCreated() {
        Country country = new Country("NewCountry");
        People people = new People("NewPeople");

        country.setPeoples(singleton(people));

        Country countryFromDb = entityManager.persistFlushFind(country);
        assertThat(countryFromDb.getPeoples().size(), equalTo(0));
    }

    @Test
    public void createCountryWithPeople_WhenPeopleNotSaved_WhenSetToPeopleAndCountry_ShouldRelationCreated() {
        Country country = new Country("NewCountry");
        People people = new People("NewPeople");

        people.setCountries(singleton(country));  // owner
        country.setPeoples(singleton(people));

        Country countryFromDb = entityManager.persistFlushFind(country);
        assertThat(countryFromDb.getPeoples().size(), equalTo(1));
    }

    @Test
    public void createCountryWithPeople_WhenPeopleSaved_WhenSetToPeople_ShouldRelationCreated() {
        Country country = new Country("NewCountry");
        People people = new People("NewPeople");
        entityManager.persistAndFlush(people);

        people.setCountries(singleton(country));  // owner

        Country countryFromDb = entityManager.persistFlushFind(country);
        assertThat(countryFromDb.getPeoples().size(), equalTo(1));
    }

    @Test
    public void createCountryWithPeople_WhenPeopleSaved_WhenSetToCountry_ShouldRelationNotCreated() {
        Country country = new Country("NewCountry");
        People people = new People("NewPeople");
        entityManager.persistAndFlush(people);

        country.setPeoples(singleton(people));

        Country countryFromDb = entityManager.persistFlushFind(country);
        assertThat(countryFromDb.getPeoples().size(), equalTo(0));
    }

    @Test
    public void createCountryWithPeople_WhenPeopleSaved_WhenSetToPeopleAndCountry_ShouldRelationCreated() {
        Country country = new Country("NewCountry");
        People people = new People("NewPeople");
        entityManager.persistAndFlush(people);

        people.setCountries(singleton(country));  // owner
        country.setPeoples(singleton(people));

        Country countryFromDb = entityManager.persistFlushFind(country);
        assertThat(countryFromDb.getPeoples().size(), equalTo(1));
    }

    @Test
    public void deletePeople_ShouldRelationObjectDeleted() {
        Country country = entityManager.find(Country.class, 1L);
        entityManager.remove(country);
        flushAndClean();

        // check than peoples deleted
        People people1 = entityManager.find(People.class, 1L);
        assertThat(people1, equalTo(null));
        People people2 = entityManager.find(People.class, 2L);
        assertThat(people2, equalTo(null));
    }
}