package hello.JsonPropertyOrder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void jsonPropertyOrder() throws JsonProcessingException {
        User user = new User(123, "firstName", "lastName");

        // serialization
        String json = new ObjectMapper().writeValueAsString(user);
        System.out.println(json); // {"last_name":"lastName","first_name":"firstName","id":123}
        assertThat(json, containsString("id"));
        assertThat(json, containsString("first_name"));
        assertThat(json, containsString("last_name"));
    }
}