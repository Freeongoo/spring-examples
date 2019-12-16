package hello.controller;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import hello.sqltracker.AssertSqlCount;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DatabaseSetup({"/dbunit/user.xml"})
public class UserCacheableControllerTest extends AbstractJpaTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }
    @Test
    public void getAll() throws Exception {
        getAllAndPrint();

        AssertSqlCount.assertSelectCount(5);
        AssertSqlCount.reset();
        flushAndClean();    // reset hibernate cache level 1

        getAllAndPrint();

        AssertSqlCount.assertSelectCount(0);
    }

    private void getAllAndPrint() throws Exception {
        this.mockMvc.perform(get(UserCacheableController.PATH))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getByOne() throws Exception {
        getByIdAndPrint();

        AssertSqlCount.assertSelectCount(4);
        AssertSqlCount.reset();
        flushAndClean();    // reset hibernate cache level 1

        getByIdAndPrint();

        AssertSqlCount.assertSelectCount(0);
    }

    private void getByIdAndPrint() throws Exception {
        this.mockMvc.perform(get(UserCacheableController.PATH + "/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk());
    }
}