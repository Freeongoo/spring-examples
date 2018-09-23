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

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username,password, enabled from users where username=?")
                .authoritiesByUsernameQuery("select username, role from user_roles where username=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/home")
                .permitAll()
            .antMatchers("/admin")
                .hasRole("ADMIN")
            .anyRequest()
                .authenticated()
                .and()
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
            .logout()
                .permitAll();

        // for handle 403 when not access
        http.exceptionHandling().accessDeniedPage("/403");
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