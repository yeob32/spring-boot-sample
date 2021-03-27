package com.example.demo.global.config;

import com.example.demo.global.security.auth.DefaultAuthenticationEntryPoint;
import com.example.demo.global.security.auth.DefaultAuthenticationFailureHandler;
import com.example.demo.global.security.auth.DefaultAuthenticationSuccessHandler;
import com.example.demo.global.security.auth.SkipPathRequestMatcher;
import com.example.demo.global.security.auth.aware.AwareAuthenticationProvider;
import com.example.demo.global.security.auth.aware.AwareTokenAuthenticationProcessingFilter;
import com.example.demo.global.security.auth.basic.BasicJwtAuthenticationProvider;
import com.example.demo.global.security.auth.basic.BasicJwtTokenAuthenticationProcessingFilter;
import com.example.demo.global.security.jwt.extractor.TokenExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String AUTHENTICATION_HEADER_NAME = "Authorization";

    public static final String BASIC_AUTHENTICATION_URL = "/auth/token";
    public static final String REFRESH_TOKEN_URL = "/auth/refresh_token";

    public static final String SIGN_IN_URL = "/api/v1/sign_in";
    public static final String SIGN_OUT_URL = "/api/v1/sign_out";

    public static final String API_ROOT_URL = "/api/**";
    public static final String AUTH_API_ROOT_URL = "/auth/**";

    private final DefaultAuthenticationEntryPoint authenticationEntryPoint;
    private final BasicJwtAuthenticationProvider basicJwtAuthenticationProvider;
    private final AwareAuthenticationProvider awareAuthenticationProvider;

    private final DefaultAuthenticationSuccessHandler authenticationSuccessHandler;
    private final DefaultAuthenticationFailureHandler authenticationFailureHandler;

    private final TokenExtractor tokenExtractor;

    protected BasicJwtTokenAuthenticationProcessingFilter jwtTokenAuthenticationProcessingFilter() throws Exception {
        List<String> pathsToSkip = Arrays.asList(BASIC_AUTHENTICATION_URL, REFRESH_TOKEN_URL);
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, API_ROOT_URL);
        BasicJwtTokenAuthenticationProcessingFilter filter
                = new BasicJwtTokenAuthenticationProcessingFilter(authenticationFailureHandler, tokenExtractor, matcher);
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    protected AwareTokenAuthenticationProcessingFilter awareAuthenticationProcessingFilter() throws Exception {
        List<String> pathsToSkip = Arrays.asList(BASIC_AUTHENTICATION_URL, SIGN_IN_URL, REFRESH_TOKEN_URL);
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, AUTH_API_ROOT_URL);
        AwareTokenAuthenticationProcessingFilter filter
                = new AwareTokenAuthenticationProcessingFilter(authenticationFailureHandler, tokenExtractor, matcher);
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
        auth.authenticationProvider(basicJwtAuthenticationProvider);
        auth.authenticationProvider(awareAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // We don't need CSRF for JWT based authentication
                .httpBasic().disable() // for Rest API
                .exceptionHandling() // 에러났을 때 처리
                .authenticationEntryPoint(this.authenticationEntryPoint)

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers(permitAllEndpointList().toArray(new String[0]))
                .permitAll()

                .and()
                .authorizeRequests()
                .antMatchers(API_ROOT_URL)
                .authenticated() // 요청에 대한 사용권한 체크

                .and()
                .addFilterBefore(jwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(awareAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    private List<String> permitAllEndpointList() {
        return Arrays.asList(
                BASIC_AUTHENTICATION_URL,
                SIGN_IN_URL,
                SIGN_OUT_URL,
                REFRESH_TOKEN_URL,
                "/console"
        );
    }
}
