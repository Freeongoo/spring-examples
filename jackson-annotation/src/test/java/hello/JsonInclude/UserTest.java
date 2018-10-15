package hello.JsonInclude;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void jsonInclude_WhenAllFieldsNull() throws JsonProcessingException {
        UserJsonIncludeNonEmpty user = new UserJsonIncludeNonEmpty();
        String jsonFieldFirstName = "first_name";
        String jsonFieldLastName = "last_name";

        // serialization
        String json = new ObjectMapper().writeValueAsString(user);
        System.out.println(json);
        assertThat(json, equalTo("{}"));
        assertThat(json, not(containsString(jsonFieldFirstName)));
        assertThat(json, not(containsString(jsonFieldLastName)));
    }

    @Test
    public void jsonInclude_WhenLastNameEmpty() throws JsonProcessingException {
        UserJsonIncludeNonEmpty user = new UserJsonIncludeNonEmpty();
        user.setFirstName("first");
        String jsonFieldFirstName = "first_name";
        String jsonFieldLastName = "last_name";

        // serialization
        String json = new ObjectMapper().writeValueAsString(user);
        System.out.println(json);
        assertThat(json, containsString(jsonFieldFirstName));
        assertThat(json, not(containsString(jsonFieldLastName)));
    }

    @Test
    public void serialization_WhenNotSetJsonInclude() throws JsonProcessingException {
        User user = new User();
        String jsonFieldFirstName = "first_name";
        String jsonFieldLastName = "last_name";

        // serialization
        String json = new ObjectMapper().writeValueAsString(user);
        System.out.println(json);
        assertThat(json, containsString(jsonFieldFirstName));
        assertThat(json, containsString(jsonFieldLastName));
    }
}