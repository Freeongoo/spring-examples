package jsonArray;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;


public class JsonArrayTest {

    @Test
    public void fromJsonStringToArray() throws IOException {
        String json = "{\"numbers\": [0, 2]}";

        JsonArray jsonArray = new ObjectMapper().readValue(json, JsonArray.class);

        Long[] expectedArray = new Long[] {0L, 2L};
        Long[] numbers = jsonArray.getNumbers();

        assertThat(numbers[0], equalTo(0L));
        assertArrayEquals(expectedArray, numbers);
    }

    @Test
    public void fromJsonStringToArray_WhenEmptyArray() throws IOException {
        String json = "{\"numbers\": []}";

        JsonArray jsonArray = new ObjectMapper().readValue(json, JsonArray.class);

        Long[] expectedArray = new Long[] {};
        Long[] numbers = jsonArray.getNumbers();

        assertArrayEquals(expectedArray, numbers);
    }

    @Test
    public void fromJsonStringToArray_WhenNull() throws IOException {
        String jsonStr = "{\"numbers\": null}";

        JsonArray jsonArray = new ObjectMapper().readValue(jsonStr, JsonArray.class);
        Long[] numbers = jsonArray.getNumbers();
        assertNull(numbers);
    }

    @Test
    public void fromJsonStringToArray_WhenArrayOfNull() throws IOException {
        String jsonStr = "{\"numbers\": [null, null]}";

        JsonArray jsonArray = new ObjectMapper().readValue(jsonStr, JsonArray.class);
        Long[] expectedArray = new Long[] {null, null};
        Long[] numbers = jsonArray.getNumbers();

        assertThat(numbers[0], equalTo(null));
        assertThat(numbers[1], equalTo(null));
        assertArrayEquals(expectedArray, numbers);
    }

    @Test
    public void fromJsonStringToArray_WhenFirstIsNull() throws IOException {
        String json = "{\"numbers\": [null, 2]}";

        JsonArray jsonArray = new ObjectMapper().readValue(json, JsonArray.class);

        Long[] expectedArray = new Long[] {null, 2L};
        Long[] numbers = jsonArray.getNumbers();

        assertThat(numbers[0], equalTo(null));
        assertThat(numbers[1], equalTo(2L));
        assertArrayEquals(expectedArray, numbers);
    }

    @Test
    public void fromJsonStringToArray_WhenSecondIsNull() throws IOException {
        String json = "{\"numbers\": [0, null]}";

        JsonArray jsonArray = new ObjectMapper().readValue(json, JsonArray.class);

        Long[] expectedArray = new Long[] {0L, null};
        Long[] numbers = jsonArray.getNumbers();

        assertThat(numbers[0], equalTo(0L));
        assertThat(numbers[1], equalTo(null));
        assertArrayEquals(expectedArray, numbers);
    }

    @Test
    public void fromJsonStringToArray_WhenEmptyStrings() throws IOException {
        String json = "{\"numbers\": [\"\", \"\"]}";

        JsonArray jsonArray = new ObjectMapper().readValue(json, JsonArray.class);

        Long[] expectedArray = new Long[] {null, null};
        Long[] numbers = jsonArray.getNumbers();

        assertThat(numbers[0], equalTo(null));
        assertThat(numbers[1], equalTo(null));
        assertArrayEquals(expectedArray, numbers);
    }

    @Test
    public void fromJsonStringToArray_WhenEmptyStringsWithSpaces() throws IOException {
        String json = "{\"numbers\": [\"  \", \"   \"]}";

        JsonArray jsonArray = new ObjectMapper().readValue(json, JsonArray.class);

        Long[] expectedArray = new Long[] {null, null};
        Long[] numbers = jsonArray.getNumbers();

        assertThat(numbers[0], equalTo(null));
        assertThat(numbers[1], equalTo(null));
        assertArrayEquals(expectedArray, numbers);
    }

    @Test(expected = JsonMappingException.class)
    public void fromJsonStringToArray_WhenInvalidJson_Before() throws IOException {
        String json = "{\"numbers\": [,2]}";

        JsonArray jsonArray = new ObjectMapper().readValue(json, JsonArray.class);
    }

    @Test(expected = JsonMappingException.class)
    public void fromJsonStringToArray_WhenInvalidJson2_After() throws IOException {
        String json = "{\"numbers\": [2,]}";

        JsonArray jsonArray = new ObjectMapper().readValue(json, JsonArray.class);
    }
}