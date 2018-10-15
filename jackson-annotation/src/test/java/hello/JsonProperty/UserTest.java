package hello.JsonProperty;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class UserTest {

    @Test
    public void jsonFormat() throws IOException {
        User user = new User("first", "last");
        String jsonFieldFirstName = "first_name";
        String jsonFieldLastName = "last";

        // serialization
        String json = new ObjectMapper().writeValueAsString(user);
        System.out.println(json);
        assertThat(json, containsString(jsonFieldFirstName));
        assertThat(json, containsString(jsonFieldLastName));

        // deserialization
        ObjectMapper mapper = new ObjectMapper();
        User actualUser = mapper.readValue(json, User.class);
        System.out.println(actualUser);
        assertThat(actualUser, equalTo(user));
    }
}