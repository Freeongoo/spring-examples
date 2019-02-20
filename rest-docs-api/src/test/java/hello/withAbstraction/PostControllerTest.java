package hello.withAbstraction;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractIntegrationMvcWithDocsSimpleRouteTest;
import hello.controller.oneToMany.PostController;
import hello.util.JacksonUtil;
import org.junit.Test;
import org.springframework.restdocs.payload.FieldDescriptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

@DatabaseSetup({"/post.xml", "/comment.xml"})
public class PostControllerTest extends AbstractIntegrationMvcWithDocsSimpleRouteTest {

    @Override
    protected String getIdentifierForGenerateSnippetsDir() {
        return "post";
    }

    @Override
    protected String getJsonBodyRequestParamsForCreateOrUpdate() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "my new post");
        return JacksonUtil.toString(map);
    }

    @Override
    protected List<FieldDescriptor> getRequestFieldDescriptorForCreateOrUpdate() {
        List<FieldDescriptor> fields = new ArrayList<>();

        fields.add(fieldWithPath("name").description("name").type(STRING));

        return fields;
    }

    @Override
    protected List<FieldDescriptor> getResponseFieldDescriptor(String prefix) {
        List<FieldDescriptor> fields = new ArrayList<>();

        fields.add(fieldWithPath(prefix + "id").description("id' post").type(NUMBER));
        fields.add(fieldWithPath(prefix + "name").description("name's post").type(STRING));
        fields.add(fieldWithPath(prefix + "comments").description("comments").type(ARRAY).optional());
        fields.add(fieldWithPath(prefix + "comments[].id").description("id's comment").type(NUMBER).optional());
        fields.add(fieldWithPath(prefix + "comments[].name").description("name's comment").type(STRING).optional());
        fields.add(fieldWithPath(prefix + "comments[].postId").description("id's parent - post").type(NUMBER).optional());

        return fields;
    }

    @Override
    protected String getBaseApiPath() {
        return PostController.PATH;
    }

    @Test
    public void getById() throws Exception {
        super.getById(1);
    }

    @Test
    public void getAll() throws Exception {
        super.getAll();
    }

    @Test
    public void create() throws Exception {
        super.create();
    }

    @Test
    public void update() throws Exception {
        super.update(1);
    }

    @Test
    public void remove() throws Exception {
        super.remove(1);
    }
}
