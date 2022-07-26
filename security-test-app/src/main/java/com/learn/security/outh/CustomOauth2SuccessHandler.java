package com.learn.security.outh;

import com.learn.model.UserDto;
import com.learn.security.JwtProvider;
import com.learn.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomOauth2SuccessHandler implements AuthenticationSuccessHandler {

  private final UserService userService;
  private final JwtProvider jwtProvider;

  @Value("${authorizedRedirectUris}")
  private  String redirectUrl;

  @Autowired
  public CustomOauth2SuccessHandler(UserService userService,
                                    JwtProvider jwtProvider) {
    this.userService = userService;
    this.jwtProvider = jwtProvider;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) {

    DefaultOAuth2User userPrincipal = (DefaultOAuth2User) authentication.getPrincipal();
    String userEmail = userPrincipal.getAttribute("email");
    UserDto userDto = userService.findUserByEmail(userEmail);

    try {
      String accessToken = jwtProvider.generateAccessToken(userDto);
      String refreshToken = jwtProvider.generateRefreshToken(userDto);

      CookieUtils.addCookie(response,"accessToken",accessToken,900);
      CookieUtils.addCookie(response,"refreshToken",refreshToken,900);

      response.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
      response.setHeader(HttpHeaders.LOCATION,redirectUrl);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
