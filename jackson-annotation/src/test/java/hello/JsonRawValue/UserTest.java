package hello.JsonRawValue;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class UserTest {

    @Test
    public void jsonRawValue() throws IOException {
        User user = new User();
        user.setName("MyName");

        user.setJson("{\"key\": \"value\"}");

        // serialization
        String json = new ObjectMapper().writeValueAsString(user);
        System.out.println(json);
        assertThat(json, containsString("key"));
        assertThat(json, containsString("name"));
    }
}