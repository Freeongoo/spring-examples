package jsonList;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class JsonListTest {

    @Test
    public void fromJsonStringToArray() throws IOException {
        String json = "{\"numbers\": [0, 2]}";

        JsonList JsonList = new ObjectMapper().readValue(json, JsonList.class);

        Long[] expectedArray = new Long[] {0L, 2L};
        List<Long> numbers = JsonList.getNumbers();

        assertThat(numbers.get(0), equalTo(0L));
        assertThat(numbers, containsInAnyOrder(expectedArray));
    }

    @Test
    public void fromJsonStringToArray_WhenEmptyArray() throws IOException {
        String json = "{\"numbers\": []}";

        JsonList JsonList = new ObjectMapper().readValue(json, JsonList.class);

        Long[] expectedArray = new Long[] {};
        List<Long> numbers = JsonList.getNumbers();

        assertThat(numbers, containsInAnyOrder(expectedArray));
    }

    @Test
    public void fromJsonStringToArray_WhenNull() throws IOException {
        String jsonStr = "{\"numbers\": null}";

        JsonList JsonList = new ObjectMapper().readValue(jsonStr, JsonList.class);
        List<Long> numbers = JsonList.getNumbers();
        assertNull(numbers);
    }

    @Test
    public void fromJsonStringToArray_WhenArrayOfNull() throws IOException {
        String jsonStr = "{\"numbers\": [null, null]}";

        JsonList JsonList = new ObjectMapper().readValue(jsonStr, JsonList.class);
        Long[] expectedArray = new Long[] {null, null};
        List<Long> numbers = JsonList.getNumbers();

        assertThat(numbers.get(0), equalTo(null));
        assertThat(numbers.get(1), equalTo(null));
        assertThat(numbers, containsInAnyOrder(expectedArray));
    }

    @Test
    public void fromJsonStringToArray_WhenFirstIsNull() throws IOException {
        String json = "{\"numbers\": [null, 2]}";

        JsonList JsonList = new ObjectMapper().readValue(json, JsonList.class);

        Long[] expectedArray = new Long[] {null, 2L};
        List<Long> numbers = JsonList.getNumbers();

        assertThat(numbers.get(0), equalTo(null));
        assertThat(numbers.get(1), equalTo(2L));
        assertThat(numbers, containsInAnyOrder(expectedArray));
    }

    @Test
    public void fromJsonStringToArray_WhenSecondIsNull() throws IOException {
        String json = "{\"numbers\": [0, null]}";

        JsonList JsonList = new ObjectMapper().readValue(json, JsonList.class);

        Long[] expectedArray = new Long[] {0L, null};
        List<Long> numbers = JsonList.getNumbers();

        assertThat(numbers.get(0), equalTo(0L));
        assertThat(numbers.get(1), equalTo(null));
        assertThat(numbers, containsInAnyOrder(expectedArray));
    }

    @Test
    public void fromJsonStringToArray_WhenEmptyStrings() throws IOException {
        String json = "{\"numbers\": [\"\", \"\"]}";

        JsonList JsonList = new ObjectMapper().readValue(json, JsonList.class);

        Long[] expectedArray = new Long[] {null, null};
        List<Long> numbers = JsonList.getNumbers();

        assertThat(numbers.get(0), equalTo(null));
        assertThat(numbers.get(1), equalTo(null));
        assertThat(numbers, containsInAnyOrder(expectedArray));
    }

    @Test
    public void fromJsonStringToArray_WhenEmptyStringsWithSpaces() throws IOException {
        String json = "{\"numbers\": [\"  \", \"   \"]}";

        JsonList JsonList = new ObjectMapper().readValue(json, JsonList.class);

        Long[] expectedArray = new Long[] {null, null};
        List<Long> numbers = JsonList.getNumbers();

        assertThat(numbers.get(0), equalTo(null));
        assertThat(numbers.get(1), equalTo(null));
        assertThat(numbers, containsInAnyOrder(expectedArray));
    }

    @Test(expected = JsonMappingException.class)
    public void fromJsonStringToArray_WhenInvalidJson_Before() throws IOException {
        String json = "{\"numbers\": [,2]}";

        JsonList JsonList = new ObjectMapper().readValue(json, JsonList.class);
    }

    @Test(expected = JsonMappingException.class)
    public void fromJsonStringToArray_WhenInvalidJson2_After() throws IOException {
        String json = "{\"numbers\": [2,]}";

        JsonList JsonList = new ObjectMapper().readValue(json, JsonList.class);
    }
}