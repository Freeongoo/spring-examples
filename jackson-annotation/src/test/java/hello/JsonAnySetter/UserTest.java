package hello.JsonAnySetter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class UserTest {

    @Test
    public void JsonAnySetter() throws IOException {
        User expectedUser = new User();
        expectedUser.setName("MyName");
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("key1", "value1");
        stringStringHashMap.put("key2", "value2");
        expectedUser.setProperties(stringStringHashMap);

        JsonObject userJsonObject = new JsonObject();
        userJsonObject.addProperty("name", "MyName");
        userJsonObject.addProperty("key1", "value1");
        userJsonObject.addProperty("key2", "value2");
        String json = userJsonObject.toString();

        // deserialization
        ObjectMapper mapper = new ObjectMapper();
        User actualUser = mapper.readValue(json, User.class);
        System.out.println(actualUser);
        assertThat(actualUser, equalTo(expectedUser));
    }
}