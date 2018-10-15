package hello.JsonView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void jsonView_WhenViewPublic() throws JsonProcessingException {
        User user = new User(123, "John", 23);

        ObjectMapper mapper = new ObjectMapper();

        String result = mapper
                .writerWithView(Views.Public.class)
                .writeValueAsString(user);

        System.out.println(result);
        assertThat(result, containsString("id"));
        assertThat(result, containsString("name"));
        assertThat(result, not(containsString("age")));
    }

    @Test
    public void jsonView_WhenViewPublic_whenDisableDefaultView() throws JsonProcessingException {
        User user = new User(123, "John", 23);

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

        String result = mapper
                .writerWithView(Views.Public.class)
                .writeValueAsString(user);

        System.out.println(result);
        assertThat(result, not(containsString("id")));
        assertThat(result, containsString("name"));
        assertThat(result, not(containsString("age")));
    }

    @Test
    public void jsonView_WhenViewPrivate() throws JsonProcessingException {
        User user = new User(123, "John", 23);

        ObjectMapper mapper = new ObjectMapper();

        String result = mapper
                .writerWithView(Views.Private.class)
                .writeValueAsString(user);

        System.out.println(result);
        assertThat(result, containsString("id"));
        assertThat(result, not(containsString("name")));
        assertThat(result, containsString("age"));
    }
}