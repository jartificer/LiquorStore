package com.example.liquorstore.security;

import com.example.liquorstore.repository.users.EndUserRepository;
import com.example.liquorstore.service.EndUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired private EndUserRepository userRepository;

  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .defaultSuccessUrl("/swagger-ui/index.html")
        .permitAll()
        .and()
        .logout()
        .permitAll()
        .and()
        .cors()
        .disable()
        .csrf()
        .disable();
  }

  @Bean
  @Override
  public UserDetailsService userDetailsService() {
    return new EndUserService(userRepository);
  }
}
