package examples;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.function.Function;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

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

            // for all routes - required authentication - it's means redirect to login page
            .anyRequest()
                .authenticated()

            .and()

            // config page login
            .formLogin()
                .loginPage("/login") // default is too "/login" - only for demonstration
                .permitAll()

            .and()

            // config page logout (default "logout")
            .logout()
                .permitAll();

        // for handle 403 when not access
        http
            .exceptionHandling()
            .accessDeniedPage("/403");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        Function<String, String> encoder = p -> passwordEncoder().encode(p);

        UserDetails user = User.withUsername("user")
                .passwordEncoder(encoder)
                .password("password")
                .roles("USER")
                .build();

        UserDetails userAdmin = User.withUsername("admin")
                .passwordEncoder(encoder)
                .password("password")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, userAdmin);
    }
}