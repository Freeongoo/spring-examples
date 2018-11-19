package createJson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class CreateJsonByJacksonTest {

    @Test
    public void createJsonByJackson_WhenSimpleInFieldNumber() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("id", 1);

        // expected
        String expected = "{\"id\":1}";

        assertThat(objectNode.toString(), equalTo(expected));
    }

    @Test
    public void createJsonByJackson_WhenSimpleInFieldString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("id", "1");

        // expected
        String expected = "{\"id\":\"1\"}";

        assertThat(objectNode.toString(), equalTo(expected));
    }

    @Test
    public void createJsonByJackson_WhenExistDateFieldAndObject() throws ParseException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode()
                .put("id", 1)
                .put("date", "2017-02-02T10:10:10");

        ObjectNode addedNode = objectNode.putObject("classWithIds")
                .put("id", 1);

        // expected
        String expected = "{\"id\":1,\"date\":\"2017-02-02T10:10:10\",\"classWithIds\":{\"id\":1}}";

        assertThat(objectNode.toString(), equalTo(expected));
    }

    @Test
    public void createJsonByJackson_WhenExistDateFieldAndCollectionObject() throws ParseException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode()
                .put("id", 1)
                .put("date", "2017-02-02T10:10:10");

        List<ObjectNode> classWithList = new ArrayList<>();
        ObjectNode em = mapper.createObjectNode();
        em.put("id", 1);
        classWithList.add(em);

        objectNode.putArray("classWithIds").addAll(classWithList);

        // expected
        String expected = "{\"id\":1,\"date\":\"2017-02-02T10:10:10\",\"classWithIds\":[{\"id\":1}]}";

        assertThat(objectNode.toString(), equalTo(expected));
    }
}
