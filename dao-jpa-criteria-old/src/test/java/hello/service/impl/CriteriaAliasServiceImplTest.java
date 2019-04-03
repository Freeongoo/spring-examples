package hello.service.impl;

import hello.container.FieldHolder;
import hello.service.CriteriaAliasService;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.*;

import static java.util.Collections.emptySet;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class CriteriaAliasServiceImplTest {

    @InjectMocks
    private CriteriaAliasService criteriaAliasService = new CriteriaAliasServiceImpl();

    @Test
    public void getCriteriaAlias_WhenFieldHolder_WhenWithoutRelationField() {
        FieldHolder fieldHolder = new FieldHolder("id", 1L);
        Set<String> criteriaAlias = criteriaAliasService.getCriteriaAlias(singletonList(fieldHolder));

        assertThat(criteriaAlias, equalTo(emptySet()));
    }

    @Test
    public void getCriteriaAlias_WhenFieldHolder_WhenWithRelationField() {
        FieldHolder fieldHolder = new FieldHolder("id", 1L, "event");
        Set<String> criteriaAlias = criteriaAliasService.getCriteriaAlias(singletonList(fieldHolder));

        Set<String> expectedAlias = new HashSet<>();
        expectedAlias.add("event");

        assertThat(criteriaAlias, containsInAnyOrder(expectedAlias.toArray()));
    }

    @Test
    public void getCriteriaAlias_WhenFieldHolder_WhenWithRelationFieldComplex() {
        FieldHolder fieldHolder = new FieldHolder("id", 1L, "event.eventType");
        Set<String> criteriaAlias = criteriaAliasService.getCriteriaAlias(singletonList(fieldHolder));

        Set<String> expectedAlias = new HashSet<>();
        expectedAlias.add("event");
        expectedAlias.add("event.eventType");

        assertThat(criteriaAlias, containsInAnyOrder(expectedAlias.toArray()));
    }

    @Test
    public void getCriteriaAlias_WhenProps_WhenWithoutRelationField() {
        Map<String, List<?>> props = new HashMap<>();
        props.put("id", singletonList(2L));

        Set<String> criteriaAlias = criteriaAliasService.getCriteriaAlias(props);

        assertThat(criteriaAlias, equalTo(emptySet()));
    }

    @Test
    public void getCriteriaAlias_WhenProps_WhenWithRelationField() {
        Map<String, List<?>> props = new HashMap<>();
        props.put("event.id", singletonList(2L));

        Set<String> criteriaAlias = criteriaAliasService.getCriteriaAlias(props);

        Set<String> expectedAlias = new HashSet<>();
        expectedAlias.add("event");

        assertThat(criteriaAlias, containsInAnyOrder(expectedAlias.toArray()));
    }

    @Test
    public void getCriteriaAlias_WhenProps_WhenWithRelationFieldComplex() {
        Map<String, List<?>> props = new HashMap<>();
        props.put("event.eventType.id", singletonList(2L));

        Set<String> criteriaAlias = criteriaAliasService.getCriteriaAlias(props);

        Set<String> expectedAlias = new HashSet<>();
        expectedAlias.add("event");
        expectedAlias.add("event.eventType");

        assertThat(criteriaAlias, containsInAnyOrder(expectedAlias.toArray()));
    }

    @Test(expected = NullPointerException.class)
    public void getCriteriaAliasByRelationFieldName_WhenNull() {
        Set<String> aliases = criteriaAliasService.getCriteriaAliasByRelationFieldName(null);
    }

    @Test
    public void getCriteriaAliasByRelationFieldName_WhenSimpleField() {
        Set<String> aliases = criteriaAliasService.getCriteriaAliasByRelationFieldName("id");
        assertThat(aliases, equalTo(emptySet()));
    }

    @Test
    public void getCriteriaAliasByRelationFieldName_WhenRelationField() {
        Set<String> aliases = criteriaAliasService.getCriteriaAliasByRelationFieldName("event.id");

        Set<String> expectedAlias = new HashSet<>();
        expectedAlias.add("event");

        assertThat(aliases, containsInAnyOrder(expectedAlias.toArray()));
    }

    @Test
    public void getCriteriaAliasByRelationFieldName_WhenRelationFieldComplex() {
        Set<String> aliases = criteriaAliasService.getCriteriaAliasByRelationFieldName("event.eventType.type.id");

        Set<String> expectedAlias = new HashSet<>();
        expectedAlias.add("event");
        expectedAlias.add("event.eventType");
        expectedAlias.add("event.eventType.type");

        assertThat(aliases, containsInAnyOrder(expectedAlias.toArray()));
    }
}