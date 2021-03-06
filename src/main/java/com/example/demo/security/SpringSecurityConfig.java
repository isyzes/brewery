package com.example.demo.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.demo.security.Roles.CONSUMER;
import static com.example.demo.security.Roles.MANAGER;


@EnableWebSecurity
@AllArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoadUserDetailService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/index", "/sign-in", "/sign-up").permitAll()
                    .antMatchers("/beers/buy", "/beers/list").hasAnyRole(CONSUMER.name(), MANAGER.name())
                    .antMatchers( "/**").hasRole(MANAGER.name())
                    .anyRequest().authenticated()
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .formLogin().disable()
                    .csrf().disable();

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
