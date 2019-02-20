package hello;

import org.junit.Before;
import org.junit.Rule;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.PathParametersSnippet;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static hello.util.JacksonUtil.prettyJson;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class AbstractIntegrationMvcWithDocsTest extends AbstractIntegrationMvcTest implements IntegrationMvcWithDocsTest {

    private static final String OUTPUT_DIRECTORY = "target/snippets";

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation(OUTPUT_DIRECTORY);

    @Before
    public void setUp() {
        super.setUp();

        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    /**
     * Name of folder for generated snippets
     * when changing the identifier do not forget to change in the file: *.adoc
     * @return String
     */
    protected abstract String getIdentifierForGenerateSnippetsDir();

    protected abstract String getJsonBodyRequestParamsForCreateOrUpdate();

    protected abstract List<FieldDescriptor> getRequestFieldDescriptorForCreateOrUpdate();

    /**
     * Don't forget when adding fields add prefix.
     * Example: fields.add(fieldWithPath(prefix + "name").description("description").type(STRING));
     *
     * @param prefix prefix
     * @return list of FieldDescriptor
     */
    protected abstract List<FieldDescriptor> getResponseFieldDescriptor(String prefix);

    @Override
    public void getById(String urlTemplate, PathParametersSnippet pathParametersSnippet, Object... urlVariables) throws Exception {
        resultActions = mockMvc.perform(get(urlTemplate, urlVariables)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        generateDocs(
                pathParametersSnippet,
                getResponseFieldsForGetById()
        );
    }

    @Override
    public void getAll(String urlTemplate, PathParametersSnippet pathParametersSnippet, Object... urlVariables) throws Exception {
        resultActions = mockMvc.perform(get(urlTemplate, urlVariables)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        if (pathParametersSnippet != null) {
            generateDocs(pathParametersSnippet, getResponseFieldsForGetAll());
        } else {
            generateDocs(getResponseFieldsForGetAll());
        }
    }

    @Override
    public void create(String urlTemplate, PathParametersSnippet pathParametersSnippet, Object... urlVariables) throws Exception {
        resultActions = mockMvc.perform(post(urlTemplate, urlVariables)
                .contentType(APPLICATION_JSON)
                .content(prettyJson(getJsonBodyRequestParamsForCreateOrUpdate())))
                .andExpect(status().isOk())
                .andDo(print());

        if (pathParametersSnippet != null) {
            generateDocs(pathParametersSnippet, getRequestFieldsForCreate(), getResponseFieldsForCreate());
        } else {
            generateDocs(getRequestFieldsForCreate(), getResponseFieldsForCreate());
        }
    }

    @Override
    public void update(String urlTemplate, PathParametersSnippet pathParametersSnippet, Object... urlVariables) throws Exception {
        resultActions = mockMvc.perform(patch(urlTemplate, urlVariables)
                .contentType(APPLICATION_JSON)
                .content(prettyJson(getJsonBodyRequestParamsForCreateOrUpdate())))
                .andExpect(status().isOk())
                .andDo(print());

        generateDocs(
                pathParametersSnippet,
                getRequestFieldsForUpdate(),
                getResponseFieldsForUpdate()
        );
    }

    @Override
    public void remove(String urlTemplate, PathParametersSnippet pathParametersSnippet, Object... urlVariables) throws Exception {
        resultActions = mockMvc.perform(delete(urlTemplate, urlVariables)
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        generateDocs(
                pathParametersSnippet
        );
    }

    protected List<FieldDescriptor> getResponseFieldsForGetById() {
        return getResponseFieldDescriptor("");
    }

    protected List<FieldDescriptor> getResponseFieldsForGetAll() {
        return getResponseFieldDescriptor("[].");
    }

    protected List<FieldDescriptor> getResponseFieldsForCreate() {
        return getResponseFieldDescriptor("");
    }

    protected List<FieldDescriptor> getResponseFieldsForUpdate() {
        return getResponseFieldDescriptor("");
    }

    protected List<FieldDescriptor> getRequestFieldsForCreate() {
        return getRequestFieldDescriptorForCreateOrUpdate();
    }

    protected List<FieldDescriptor> getRequestFieldsForUpdate() {
        return getRequestFieldDescriptorForCreateOrUpdate();
    }

    private String getDocsIdentifier() {
        return getIdentifierForGenerateSnippetsDir() + "/{method-name}";
    }

    protected void generateDocs(PathParametersSnippet pathParameters) throws Exception {
        resultActions.andDo(document(
                getDocsIdentifier(),
                pathParameters
            )
        );
    }

    protected void generateDocs(PathParametersSnippet pathParameters, List<FieldDescriptor> responseFields) throws Exception {
        resultActions.andDo(document(
                getDocsIdentifier(),
                pathParameters,
                responseFields(responseFields)
            )
        );
    }

    protected void generateDocs(List<FieldDescriptor> responseFields) throws Exception {
        resultActions.andDo(document(
                getDocsIdentifier(),
                responseFields(responseFields)
            )
        );
    }

    protected void generateDocs(List<FieldDescriptor> requestedFields, List<FieldDescriptor> responseFields) throws Exception {
        resultActions.andDo(document(
                getDocsIdentifier(),
                requestFields(requestedFields),
                responseFields(responseFields)
            )
        );
    }

    protected void generateDocs(PathParametersSnippet pathParameters, List<FieldDescriptor> requestedFields, List<FieldDescriptor> responseFields) throws Exception {
        resultActions.andDo(document(
                getDocsIdentifier(),
                pathParameters,
                requestFields(requestedFields),
                responseFields(responseFields)
            )
        );
    }
}
