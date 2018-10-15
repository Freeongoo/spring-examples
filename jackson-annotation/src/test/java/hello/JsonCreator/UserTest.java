package hello.JsonCreator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void jsonCreator() throws IOException {
        User user = new User("first", "last");
        String jsonFieldFirstName = "first_some_name";
        String jsonFieldLastName = "last_some_name";

        JsonObject userJsonObject = new JsonObject();
        userJsonObject.addProperty(jsonFieldFirstName, "first");
        userJsonObject.addProperty(jsonFieldLastName, "last");
        String json = userJsonObject.toString();

        // deserialization
        ObjectMapper mapper = new ObjectMapper();
        User actualUser = mapper.readValue(json, User.class);
        System.out.println(actualUser);
        assertThat(actualUser, equalTo(user));
    }
}