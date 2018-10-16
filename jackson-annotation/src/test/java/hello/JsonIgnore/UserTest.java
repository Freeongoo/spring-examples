package hello.JsonIgnore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void JsonIgnore() throws IOException {
        User user = new User("first", "last");

        // serialization
        String json = new ObjectMapper().writeValueAsString(user);
        System.out.println(json); // {"last_name":"last"}
        assertThat(json, not(containsString("first_name")));
        assertThat(json, containsString("last_name"));

        User expectedUser = new User(null, "last");

        // deserialization
        ObjectMapper mapper = new ObjectMapper();
        User actualUser = mapper.readValue(json, User.class);
        System.out.println(actualUser); // User{firstName='null', lastName='last'}
        assertThat(actualUser, equalTo(expectedUser));
    }
}