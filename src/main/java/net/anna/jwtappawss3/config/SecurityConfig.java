package net.anna.jwtappawss3.config;

import net.anna.jwtappawss3.security.jwt.JwtConfigurer;
import net.anna.jwtappawss3.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final JwtTokenProvider jwtTokenProvider;

//    private static final String ADMIN_ENDPOINT="/api/v1/admin/**";
//    private static final String MODERATOR_ENDPOINT="/api/v1/moderator/**";
    private static final String LOGIN_ENDPOINT ="/api/v1/auth/login";
    private static final String FILES_ENDPOINT ="/api/v1/files/**";
    private static final String USERS_ENDPOINT ="/api/v1/users/**";
    private static final String EVENTS_ENDPOINT ="/api/v1/events/**";


    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider=jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()

                .antMatchers(LOGIN_ENDPOINT).permitAll()

                .antMatchers(HttpMethod.GET, USERS_ENDPOINT).access("hasRole('ADMIN') ")
                .antMatchers(HttpMethod.DELETE, USERS_ENDPOINT).access(" hasRole('ADMIN') ")
                .antMatchers(HttpMethod.POST, USERS_ENDPOINT).access(" hasRole('ADMIN') ")
                .antMatchers(HttpMethod.PUT, USERS_ENDPOINT).access("hasRole('ADMIN') ")

                .antMatchers(HttpMethod.GET, EVENTS_ENDPOINT).access("hasRole('ADMIN') ")
                .antMatchers(HttpMethod.DELETE, EVENTS_ENDPOINT).access(" hasRole('ADMIN') ")
                .antMatchers(HttpMethod.POST, EVENTS_ENDPOINT).access(" hasRole('ADMIN') ")
                .antMatchers(HttpMethod.PUT,EVENTS_ENDPOINT).access("hasRole('ADMIN') ")

                .antMatchers(HttpMethod.GET, FILES_ENDPOINT).access("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER') ")
                .antMatchers(HttpMethod.DELETE, FILES_ENDPOINT).access("hasRole('MODERATOR') or hasRole('ADMIN') ")
                .antMatchers(HttpMethod.POST, FILES_ENDPOINT).access("hasRole('MODERATOR') or hasRole('ADMIN') ")
                .antMatchers(HttpMethod.PUT, FILES_ENDPOINT).access("hasRole('MODERATOR') or hasRole('ADMIN') ")

                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));


    }
}
