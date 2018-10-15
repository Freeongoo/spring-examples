package hello.JsonAlias;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class UserTest {

    @Test
    public void jsonAlias() throws IOException {
        User user = new User("first", "last");
        String jsonFieldFirstName = "first";
        String jsonFieldLastName = "last";

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

    @Test
    public void jsonAlias_WhenShortNameAlias() throws IOException {
        User user = new User("first", "last");
        String jsonFieldFirstName = "fn";
        String jsonFieldLastName = "ln";

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