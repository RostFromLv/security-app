package com.learn.outh;

import static com.learn.outh.HttpCookieOauth2AuthorizationRequestRepository.redirectUriParamCookieName;

import com.learn.security.JwtProvider;
import com.learn.service.UserService;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class Oauth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JwtProvider jwtProvider;
  private final UserService userService;
  private final HttpCookieOauth2AuthorizationRequestRepository httpCookieOauth2AuthorizationRequestRepository;

  @Value("${authorizedRedirectUris}")
  private List<String> authorizedRedirectUris;

  @Autowired
  public Oauth2LoginSuccessHandler(JwtProvider jwtProvider,
                                   UserService userService,
                                   HttpCookieOauth2AuthorizationRequestRepository httpCookieOauth2AuthorizationRequestRepository) {
    this.jwtProvider = jwtProvider;
    this.userService = userService;
    this.httpCookieOauth2AuthorizationRequestRepository =
        httpCookieOauth2AuthorizationRequestRepository;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication)  throws IOException {

    System.out.println("Success authentication!");
    String targetUrl = null;
    try {
      targetUrl = determinateTargetUrl(request,response,authentication);
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (response.isCommitted()){
      logger.debug("Response has already been commited. Unable to redirect to: "+targetUrl);
    }
    response.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
    clearAuthenticationAttributes(request,response);
    getRedirectStrategy().sendRedirect(request,response,targetUrl);
  }

  protected String determinateTargetUrl(HttpServletRequest request,HttpServletResponse response,Authentication authentication)
      throws Exception {
    Optional<String> redirectUri = CookieUtils.getCookie(request, redirectUriParamCookieName).map(Cookie::getValue);
    if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())){
      throw new RequestRejectedException("Sorry! Wrong redirect URI and we can`t proceed with the authentication");
    }
    String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
    String token  = jwtProvider.generateAccessToken(userService.findUserByEmail(authentication.getName()));
    String refreshToken = jwtProvider.generateRefreshToken(userService.findUserByEmail(authentication.getName()));
    return UriComponentsBuilder.fromUriString(targetUrl)
        .queryParam("token",token)
        .queryParam("refreshToken",refreshToken)
        .build().toString();
  }

  protected void clearAuthenticationAttributes(HttpServletRequest request,HttpServletResponse response){
    super.clearAuthenticationAttributes(request);
    httpCookieOauth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request,response);
  }

  private boolean isAuthorizedRedirectUri(String uri){
    URI clientRedirectUri = URI.create(uri);

    return authorizedRedirectUris
        .stream()
        .anyMatch(authorizedRedirectUri -> {
          URI authorizedURI = URI.create(authorizedRedirectUri);
          return authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
              && authorizedURI.getPort() == clientRedirectUri.getPort();
        });
  }

}
