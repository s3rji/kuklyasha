package ru.serji.kuklyasha.config;

import com.fasterxml.jackson.databind.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.*;
import ru.serji.kuklyasha.security.jwt.*;
import ru.serji.kuklyasha.web.util.*;

import static ru.serji.kuklyasha.web.util.UserUtil.*;

@Configuration
@EnableWebSecurity
@Slf4j
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String IMAGES_ENDPOINT = "/doll-images/**";
    private static final String LOGIN_ENDPOINT = "/api/auth/login";
    private static final String PROFILE_ENDPOINT = "/api/profile";
    private static final String ADMIN_ENDPOINT = "/api/admin/**";
    private static final String CATALOG_ENDPOINT = "/api/dolls/**";

    private final JwtTokenFilter filter;
    private final UserDetailsService userDetailsService;

    @Autowired
    private void setMapper(ObjectMapper objectMapper) {
        JsonUtil.setMapper(objectMapper);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint(HttpStatus.UNAUTHORIZED);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(PASSWORD_ENCODER);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(IMAGES_ENDPOINT).permitAll()
                .antMatchers(CATALOG_ENDPOINT).permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .antMatchers(LOGIN_ENDPOINT).anonymous()
                .antMatchers(HttpMethod.POST, PROFILE_ENDPOINT).anonymous()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint());
    }
}