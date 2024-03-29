package com.learn.configs;

import com.learn.jwt.AuthenticationFilter;
import com.learn.jwt.PostgresUser;
import com.learn.jwt.converter.AuthenticationConverterChain;
import com.learn.jwt.converter.BearerTokenAuthenticationConverter;
import com.learn.jwt.converter.CookieJWTConverter;
import com.learn.security.JwtProvider;
import com.learn.security.outh.CustomOauth2SuccessHandler;
import com.learn.security.outh.CustomOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtProvider jwtProvider;
  private final PostgresUser userDetailsService;
  private final CustomOauth2SuccessHandler successHandler;
  private final CustomOauth2UserService customOauth2UserService;


  @Autowired
  public WebSecurityConfig(JwtProvider jwtProvider,
                           PostgresUser userDetailsService,
                           CustomOauth2SuccessHandler successHandler,
                           CustomOauth2UserService customOauth2UserService) {
    this.jwtProvider = jwtProvider;
    this.userDetailsService = userDetailsService;
    this.successHandler = successHandler;
    this.customOauth2UserService = customOauth2UserService;
  }


  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http = http.cors().and().csrf().disable();

    http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

    http
        .authorizeRequests()
        .antMatchers("/oauth2/**").permitAll()
        .antMatchers("/login/*").permitAll()
        .anyRequest().authenticated()

        .and().formLogin()
        .and().oauth2Login()
        .userInfoEndpoint().userService(customOauth2UserService)
        .and()
        .successHandler(successHandler);

    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(noOpPasswordEncoder());

    AuthenticationFilter filter = new AuthenticationFilter(getAuthenticationConverterChain(), authenticationProvider);

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

  private AuthenticationConverterChain getAuthenticationConverterChain(){
    AuthenticationConverterChain converterChain = new AuthenticationConverterChain();

    AuthenticationConverter bearerConverter = new BearerTokenAuthenticationConverter(jwtProvider);
    converterChain.addConverter(bearerConverter);

    AuthenticationConverter cookieJwtConverter = new CookieJWTConverter();
    converterChain.addConverter(cookieJwtConverter);

    return converterChain;
  }

}
