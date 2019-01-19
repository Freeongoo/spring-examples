package examples.withMockUser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationSecurityTest {

    private static String DOMAIN = "http://localhost";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void givenOnPublicUrl_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenOnPublicUrlHome_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/home"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAuthRequestOnPrivateAdminServiceWithoutUser_shouldForbiddenWith403() throws Exception {
        mvc.perform(get("/admin"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void givenAuthRequestOnPrivateAdminServiceWithAdminByHttpBasic_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/admin")
                .with(httpBasic("admin", "password")))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAuthRequestOnPrivateAdminServiceWithUserByHttpBasic_shouldForbiddenWith403() throws Exception {
        mvc.perform(get("/admin")
                .with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username="user",roles={"USER"})
    @Test
    public void givenOnPublicUrlHomeWithUser_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/home"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username="admin",roles={"ADMIN"})
    @Test
    public void givenAuthRequestOnPrivateAdminServiceWithAdmin_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/admin"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username="user",roles={"USER"})
    @Test
    public void givenAuthRequestOnPrivateAdminServiceWithUser_shouldForbiddenWith403() throws Exception {
        mvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenOnNotPublicUrl_shouldUnauthorized401() throws Exception {
        mvc.perform(get("/hello"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(username="user",roles={"USER"})
    @Test
    public void givenOnNotPublicUrlWithUser_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/hello"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenOnNotExistUrl_shouldUnauthorized401() throws Exception {
        mvc.perform(get("/not-exist"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(username="user",roles={"USER"})
    @Test
    public void givenOnNotExistUrlWithUser_shouldNotFound404() throws Exception {
        mvc.perform(get("/not-exist"))
                .andExpect(status().isNotFound());
    }
}