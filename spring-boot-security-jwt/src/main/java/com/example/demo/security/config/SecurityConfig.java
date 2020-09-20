package com.example.demo.security.config;

import com.example.demo.security.auth.RestAuthenticationEntryPoint;
import com.example.demo.security.auth.SkipPathRequestMatcher;
import com.example.demo.security.auth.jwt.JwtAuthenticationProvider;
import com.example.demo.security.auth.jwt.JwtTokenAuthenticationProcessingFilter;
import com.example.demo.security.auth.jwt.extractor.TokenExtractor;
import com.example.demo.security.auth.rest.RestAuthenticationProvider;
import com.example.demo.security.auth.rest.RestLoginProcessingFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
    public static final String AUTHENTICATION_API_ROOT_URL = "/api/member/**";
    public static final String LOGIN_URL = "/api/login";
    public static final String REFRESH_TOKEN_URL = "/api/auth/token";

    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final AuthenticationFailureHandler failureHandler;
    private final AuthenticationSuccessHandler successHandler;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final RestAuthenticationProvider restAuthenticationProvider;
    private final TokenExtractor tokenExtractor;
    private final ObjectMapper objectMapper;

    protected RestLoginProcessingFilter buildRestLoginProcessingFilter() throws Exception {
        RestLoginProcessingFilter filter = new RestLoginProcessingFilter(LOGIN_URL, successHandler, failureHandler, objectMapper);
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter(List<String> pathsToSkip) throws Exception {
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, AUTHENTICATION_API_ROOT_URL);
        JwtTokenAuthenticationProcessingFilter filter
                = new JwtTokenAuthenticationProcessingFilter(failureHandler, tokenExtractor, matcher);
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(jwtAuthenticationProvider);
        auth.authenticationProvider(restAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<String> permitAllEndpointList = Arrays.asList(
                LOGIN_URL,
                REFRESH_TOKEN_URL,
                "/console"
        );

        http
                .csrf().disable() // We don't need CSRF for JWT based authentication
                .exceptionHandling()
                .authenticationEntryPoint(this.authenticationEntryPoint)

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers(AUTHENTICATION_API_ROOT_URL).authenticated() // Protected API End-points
                .and()
                .authorizeRequests() // 요청에 대한 사용권한 체크
                .anyRequest()
//                .antMatchers(permitAllEndpointList.toArray(new String[0]))
                .permitAll()
                .and()
                .addFilterBefore(buildRestLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(permitAllEndpointList),
                        UsernamePasswordAuthenticationFilter.class);

    }
}
