package hello.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder userPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            // disable CSRF protection
            .csrf()
                .disable()

            .authorizeRequests()

            .antMatchers(HttpMethod.GET, "/api/company/**").hasAnyAuthority(
                Permissions.COMPANY_VIEW.getValue())

            .antMatchers(HttpMethod.POST, "/api/company/").hasAnyAuthority(
                Permissions.COMPANY_CREATE.getValue())

            // "/?*" - means one or more chars
            .antMatchers(HttpMethod.PATCH, "/api/company/?*").hasAnyAuthority(
                Permissions.COMPANY_EDIT.getValue())

            .antMatchers(HttpMethod.DELETE, "/api/company/?*").hasAnyAuthority(
                Permissions.COMPANY_CREATE.getValue())

            .anyRequest()
                .authenticated()

            .and()

            // enable Http Basic and add custom entryPoint
            .httpBasic();

        // @formatter:on
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        Function<String, String> encoder = p -> userPasswordEncoder.encode(p);

        UserDetails user = createUser(encoder);
        UserDetails userAdmin = createAdmin(encoder);

        return new InMemoryUserDetailsManager(user, userAdmin);
    }

    private UserDetails createAdmin(Function<String, String> encoder) {
        List<GrantedAuthority> adminGrantedAuthorities = new ArrayList<>();
        adminGrantedAuthorities.add(new SimpleGrantedAuthority(Permissions.COMPANY_VIEW.getValue()));
        adminGrantedAuthorities.add(new SimpleGrantedAuthority(Permissions.COMPANY_EDIT.getValue()));
        adminGrantedAuthorities.add(new SimpleGrantedAuthority(Permissions.COMPANY_CREATE.getValue()));

        return User.withUsername("admin")
                .passwordEncoder(encoder)
                .password("admin")
                .authorities(adminGrantedAuthorities)
                .build();
    }

    private UserDetails createUser(Function<String, String> encoder) {
        List<GrantedAuthority> userGrantedAuthorities = new ArrayList<>();
        userGrantedAuthorities.add(new SimpleGrantedAuthority(Permissions.COMPANY_VIEW.getValue()));

        return User.withUsername("user")
                .passwordEncoder(encoder)
                .password("user")
                .authorities(userGrantedAuthorities)
                .build();
    }
}
