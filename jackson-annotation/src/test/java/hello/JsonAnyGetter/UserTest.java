package hello.JsonAnyGetter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void JsonAnyGetter() throws JsonProcessingException {
        User user = new User();
        user.setName("MyName");

        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("key1", "value1");
        stringStringHashMap.put("key2", "value2");
        user.setProperties(stringStringHashMap);

        // serialization
        String json = new ObjectMapper().writeValueAsString(user);
        System.out.println(json);
        assertThat(json, containsString("name"));
        assertThat(json, containsString("key1"));
        assertThat(json, containsString("key2"));
        assertThat(json, not(containsString("properties")));
    }

    @Test
    public void serialize_WhenFieldMap_WhenNotJsonAndGetter() throws JsonProcessingException {
        UserWithoutJsonAndGetter user = new UserWithoutJsonAndGetter();
        user.setName("MyName");

        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("key1", "value1");
        stringStringHashMap.put("key2", "value2");
        user.setProperties(stringStringHashMap);

        // serialization
        String json = new ObjectMapper().writeValueAsString(user);
        System.out.println(json);
        assertThat(json, containsString("name"));
        assertThat(json, containsString("key1"));
        assertThat(json, containsString("key2"));
        assertThat(json, containsString("properties"));
    }
}