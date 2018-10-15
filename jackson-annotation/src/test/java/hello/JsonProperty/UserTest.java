package hello.JsonProperty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void jsonFormat() throws JsonProcessingException {
        User user = new User("first", "last");
        String jsonFieldFirstName = "first_name";
        String jsonFieldLastName = "last";

        // serialization
        String json = new ObjectMapper().writeValueAsString(user);
        System.out.println(json);
        assertThat(json, containsString(jsonFieldFirstName));
        assertThat(json, containsString(jsonFieldLastName));
    }
}