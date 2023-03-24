package com.example.basicauthen.config;

import com.example.basicauthen.Constant.EnableTokenPath;
import com.example.basicauthen.config.JwtConfig.JwtAuthenticationFilter;
import com.example.basicauthen.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailServiceImpl userDetailsService;
    @Autowired
    private RateLimitFilter rateLimitFilter;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    .authorizeRequests()
                .antMatchers("/createToken").permitAll()
                .antMatchers(EnableTokenPath.report).hasAuthority("RENTESEG")
                .antMatchers(EnableTokenPath.reportUser).hasAuthority("RENTESEG")
                .antMatchers(EnableTokenPath.demo).hasAuthority("ADMIN")
                .and()
                //HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .formLogin().disable();
        http.addFilterBefore(rateLimitFilter, BasicAuthenticationFilter.class).exceptionHandling();
        http.addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class).exceptionHandling();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
