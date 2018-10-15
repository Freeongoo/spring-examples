package hello.JsonIgnoreProperties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.google.gson.JsonObject;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class UserIgnoreUnknownTest {

    @Test
    public void jsonIgnoreProperties() throws IOException {
        UserIgnoreUnknown userIgnoreUnknown = new UserIgnoreUnknown("first", "last");

        JsonObject userJsonObject = new JsonObject();
        userJsonObject.addProperty("first_name", "first");
        userJsonObject.addProperty("last_name", "last");
        userJsonObject.addProperty("not_exist_key", "somethx");
        String json = userJsonObject.toString();

        // deserialization
        ObjectMapper mapper = new ObjectMapper();
        UserIgnoreUnknown actualUserIgnoreUnknown = mapper.readValue(json, UserIgnoreUnknown.class);
        System.out.println(actualUserIgnoreUnknown);
        assertThat(actualUserIgnoreUnknown, equalTo(userIgnoreUnknown));
    }

    @Test(expected = UnrecognizedPropertyException.class)
    public void jsonIgnoreProperties_WhenNotIgnore() throws IOException {
        User user = new User("first", "last");

        JsonObject userJsonObject = new JsonObject();
        userJsonObject.addProperty("first_name", "first");
        userJsonObject.addProperty("last_name", "last");
        userJsonObject.addProperty("not_exist_key", "somethx");
        String json = userJsonObject.toString();

        // deserialization
        ObjectMapper mapper = new ObjectMapper();
        User actualUserIgnoreUnknown = mapper.readValue(json, User.class);
        System.out.println(actualUserIgnoreUnknown);
        assertThat(actualUserIgnoreUnknown, equalTo(user));
    }
}