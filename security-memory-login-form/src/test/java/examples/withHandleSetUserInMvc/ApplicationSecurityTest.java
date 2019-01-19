package examples.withHandleSetUserInMvc;

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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
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
    public void givenOnPublicUrlHomeWithUser_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/home").with(user("user").roles("USER")))
                .andExpect(status().isOk());
    }

    @Test
    public void givenOnPublicLoginUrl_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenOnPostLoginUrlWithoutCsrf_shouldForbiddenWith403() throws Exception {
        mvc.perform(post("/login"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenOnPostLoginUrlWithCsrfWithoutUser_shouldRedirect302_ToErrorUrl() throws Exception {
        mvc.perform(post("/login").with(csrf()))
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(status().isFound());
    }

    @Test
    public void givenOnPostLoginUrlSuccess_shouldRedirect302_ToRootUrl() throws Exception {
        mvc.perform(formLogin("/login").user("admin").password("password"))
                .andExpect(redirectedUrl("/"))
                .andExpect(status().isFound());
    }

    @Test
    public void givenAuthRequestOnPrivateAdminServiceWithAdmin_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/admin").with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAuthRequestOnPrivateAdminServiceWithUser_shouldForbiddenWith403() throws Exception {
        mvc.perform(get("/admin").with(user("user").roles("USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenOnNotPublicUrl_shouldRedirect302() throws Exception {
        mvc.perform(get("/hello"))
                .andExpect(redirectedUrl(DOMAIN + "/login"))
                .andExpect(status().isFound());
    }

    @Test
    public void givenOnNotPublicUrlWithUser_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/hello").with(user("user").roles("USER")))
                .andExpect(status().isOk());
    }

    @Test
    public void givenOnNotExistUrl_shouldRedirect302() throws Exception {
        mvc.perform(get("/not-exist"))
                .andExpect(redirectedUrl(DOMAIN + "/login"))
                .andExpect(status().isFound());
    }

    @Test
    public void givenOnNotExistUrlWithUser_shouldNotFound404() throws Exception {
        mvc.perform(get("/not-exist").with(user("user").roles("USER")))
                .andExpect(status().isNotFound());
    }
}