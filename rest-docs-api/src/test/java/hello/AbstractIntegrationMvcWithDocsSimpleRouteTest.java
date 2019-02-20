package hello;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

/**
 * Simple route - without additional {id} in route.
 * Example: "/api/home/"
 */
public abstract class AbstractIntegrationMvcWithDocsSimpleRouteTest extends AbstractIntegrationMvcWithDocsTest {

    public void getById(Integer id) throws Exception {
        super.getById(
                getBaseApiPath() + "/{id}",
                pathParameters(
                        parameterWithName("id").description("The entity's id")
                ),
                id);
    }

    public void getAll() throws Exception {
        super.getAll(
                getBaseApiPath(),
                null
        );
    }

    public void create() throws Exception {
        super.create(
                getBaseApiPath(),
                null
        );
    }

    public void update(Integer id) throws Exception {
        super.update(getBaseApiPath() + "/{id}",
                pathParameters(
                        parameterWithName("id").description("The entity's id")
                ),
                id);
    }

    public void remove(Integer id) throws Exception {
        super.remove(
                getBaseApiPath() + "/{id}",
                pathParameters(
                        parameterWithName("id").description("The entity's id")
                ),
                id
        );
    }
}
