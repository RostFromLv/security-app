package com.learn.configs;

import com.learn.jwt.AuthenticationFilter;
import com.learn.jwt.BearerTokenAuthenticationConverter;
import com.learn.jwt.PostgresUser;
import com.learn.outh.CustomOauth2UserService;
import com.learn.outh.Oauth2LoginSuccessHandler;
import com.learn.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtProvider jwtProvider;
  private final PostgresUser userDetailsService;
  private final CustomOauth2UserService userService;
  private final Oauth2LoginSuccessHandler successHandler;

  @Autowired
  public WebSecurityConfig(JwtProvider jwtProvider,
                           PostgresUser userDetailsService,
                           CustomOauth2UserService userService,
                           Oauth2LoginSuccessHandler successHandler) {
    this.jwtProvider = jwtProvider;
    this.userDetailsService = userDetailsService;
    this.userService = userService;
    this.successHandler = successHandler;
  }


  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http = http.cors().and().csrf().disable();

    http
        .authorizeRequests()
        .antMatchers("/oauth2/**").permitAll()
        .antMatchers("/login*").permitAll()
        .anyRequest().authenticated()
        .and().formLogin()
        .and().oauth2Login()
        .userInfoEndpoint().userService(userService)
        .and()
        .successHandler(successHandler);

    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(noOpPasswordEncoder());
    AuthenticationConverter converter = new BearerTokenAuthenticationConverter(jwtProvider);
    AuthenticationFilter filter = new AuthenticationFilter(converter, authenticationProvider);

    http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
  }

  private PasswordEncoder noOpPasswordEncoder() {
    return new PasswordEncoder() {

      @Override
      public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
      }

      @Override
      public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.equals(encodedPassword);
      }
    };
  }

}
