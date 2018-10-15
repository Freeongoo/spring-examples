package hello.JsonFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class UserTest {

    @Test
    public void jsonFormat() throws IOException, ParseException {
        String dateStr = "01-09-2018 12:12:12";
        SimpleDateFormat df = new SimpleDateFormat(User.DATE_FORMAT);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = df.parse(dateStr);

        User user = new User("first", "last", date);

        // serialization
        String json = new ObjectMapper().writeValueAsString(user);
        System.out.println(json);
        assertThat(json, containsString(dateStr));

        // deserialization
        ObjectMapper mapper = new ObjectMapper();
        User actualUser = mapper.readValue(json, User.class);
        System.out.println(actualUser);
        assertThat(actualUser, equalTo(user));
    }
}