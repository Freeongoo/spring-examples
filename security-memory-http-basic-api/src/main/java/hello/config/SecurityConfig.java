package hello.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.function.Function;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder userPasswordEncoder;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    /*@Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }*/

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(userPasswordEncoder);
        auth.authenticationProvider(authProvider);
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // disable CSRF protection
                .csrf()
                .disable()

                .authorizeRequests()

                // when try access to route "/api/company" - must have role "ADMIN"
                .antMatchers("/api/company/")
                    .hasRole("ADMIN")

                .anyRequest()
                .authenticated()

                .and()

                // enable Http Basic and add custom entryPoint
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint);

        /*http
            // run config
            .authorizeRequests()

            // access to routes allow all
            //.antMatchers("/", "/api/encode/**")
            //    .permitAll()

            // when try access to route "/api/company" - must have role "ADMIN"
            //.antMatchers("/api/company/")
            //    .hasRole("ADMIN")

            // for all routes - required authentication
            .anyRequest()
                .authenticated()

            .and()

            // config using http basic
            .httpBasic();*/
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        Function<String, String> encoder = p -> userPasswordEncoder.encode(p);

        UserDetails user = User.withUsername("user")
                .passwordEncoder(encoder)
                .password("user")
                .roles("USER")
                .build();

        UserDetails userAdmin = User.withUsername("admin")
                .passwordEncoder(encoder)
                .password("admin")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, userAdmin);
    }
}
