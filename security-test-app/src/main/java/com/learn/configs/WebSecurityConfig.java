package com.learn.configs;

import com.learn.jwt.AuthenticationFilter;
import com.learn.jwt.BearerTokenAuthenticationConverter;
import com.learn.jwt.PersistentUserDetailsService;
import com.learn.security.JwtProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtProvider jwtProvider;
  private final PersistentUserDetailsService userDetailsService;

  public WebSecurityConfig(JwtProvider jwtProvider,
                           PersistentUserDetailsService userDetailsService) {
    this.jwtProvider = jwtProvider;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http = http.cors().and().csrf().disable();

    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.exceptionHandling()
        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

    http.authorizeRequests()
        .antMatchers("/api/v1/auth/**", "/api/v1/users/**").permitAll()
        .anyRequest().authenticated();


    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

    authenticationProvider.setUserDetailsService(userDetailsService);

    AuthenticationConverter converter = new BearerTokenAuthenticationConverter();//-CHANGE LOGIC and change param
    AuthenticationFilter filter = new AuthenticationFilter(converter, authenticationProvider);

    http.addFilterAt(filter, UsernamePasswordAuthenticationFilter.class);
  }
}
