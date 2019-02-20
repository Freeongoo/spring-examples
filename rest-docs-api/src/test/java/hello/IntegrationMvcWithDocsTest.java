package hello;

import org.springframework.restdocs.request.PathParametersSnippet;

public interface IntegrationMvcWithDocsTest {

    void getById(String urlTemplate, PathParametersSnippet pathParametersSnippet, Object... urlVariables) throws Exception;

    void getAll(String urlTemplate, PathParametersSnippet pathParametersSnippet, Object... urlVariables) throws Exception;

    void create(String urlTemplate, PathParametersSnippet pathParametersSnippet, Object... urlVariables) throws Exception;

    void update(String urlTemplate, PathParametersSnippet pathParametersSnippet, Object... urlVariables) throws Exception;

    void remove(String urlTemplate, PathParametersSnippet pathParametersSnippet, Object... urlVariables) throws Exception;
}
