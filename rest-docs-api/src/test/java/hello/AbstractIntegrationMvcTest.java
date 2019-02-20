package hello;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

public abstract class AbstractIntegrationMvcTest extends AbstractIntegrationForDbUnitTest {

    protected MockMvc mockMvc;
    protected Principal principal;  // for auth if needed
    protected ResultActions resultActions;

    @Autowired
    protected WebApplicationContext context;

    @Before
    public void setUp() {
        super.setUp();

        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    protected abstract String getBaseApiPath();
}
