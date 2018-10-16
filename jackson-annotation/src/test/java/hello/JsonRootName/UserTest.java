package hello.JsonRootName;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class UserTest {

    @Test
    public void JsonRootName() throws IOException {
        User user = new User("first", "last");
        String jsonFieldFirstName = "first_name";
        String jsonFieldLastName = "last_name";

        // serialization
        String json = new ObjectMapper()
                .enable(SerializationFeature.WRAP_ROOT_VALUE)
                .writeValueAsString(user);
        System.out.println(json);
        assertThat(json, containsString("usr"));
        assertThat(json, containsString(jsonFieldFirstName));
        assertThat(json, containsString(jsonFieldLastName));
    }
}