package com.nogueira4j.movies.battle.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.headers().frameOptions().disable().and()
                .csrf().disable()
                .authorizeRequests().antMatchers("/player/login").permitAll()
                .and()
                .requestMatchers().antMatchers("/games/**")
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception
    {
        auth.inMemoryAuthentication()
                .withUser("Fulano")
                .password("{noop}fulano123")
                .roles("USER")
                .and()
                .withUser("Ciclano")
                .password("{noop}ciclano123")
                .roles("USER");
    }
}