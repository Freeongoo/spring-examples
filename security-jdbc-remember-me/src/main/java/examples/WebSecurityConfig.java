package examples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    /**
     * Add query request to database for get info user by username and authorities by username
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery("select username, password, enabled from users where username = ?")
            .authoritiesByUsernameQuery("select username, role from user_roles where username = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // run config
            .authorizeRequests()

            // when try access to routes "/" or "/home" - allow all
            .antMatchers("/", "/home")
                .permitAll()

            // when try access to route "/admin" - must have role "ADMIN"
            .antMatchers("/admin")
                .hasRole("ADMIN")

            // for all routes - required authentication
            .anyRequest()
                .authenticated()

            .and()

            // config page login
            .formLogin()
                .loginPage("/login")
                .permitAll()

            .and()

            // fot db store rememberMe
            .rememberMe()
                .rememberMeParameter("remember-me") // default "remember-me" it's for examples :)
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(86400)

            // for cookie store rememberMe
            /*.rememberMe()
                .rememberMeParameter("remember-me") // default "remember-me" it's for examples :)
                .key("uniqueAndSecret")
                .rememberMeCookieName("javasampleapproach-remember-me")
                .tokenValiditySeconds(24 * 60 * 60)*/
                .and()

            // config page logout (default "logout")
            .logout()
                .permitAll();

        // for handle 403 when not access
        http
            .exceptionHandling()
            .accessDeniedPage("/403");
    }

    // set PersistentTokenRepository for store rememberMe
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}