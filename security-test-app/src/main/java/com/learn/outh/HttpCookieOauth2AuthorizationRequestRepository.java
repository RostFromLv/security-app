package com.learn.outh;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

@Component
public class HttpCookieOauth2AuthorizationRequestRepository implements
    AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

  public final static String oauth2AuthorizationRequestCookieName = "oauth2_auth_request";
  public static final String redirectUriParamCookieName = "redirect_uri";
  private static final int cookieExpiredSeconds = 120;

  @Override
  public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
    return  CookieUtils.getCookie(request, oauth2AuthorizationRequestCookieName)
        .map(cookie -> CookieUtils.deserialize(cookie,OAuth2AuthorizationRequest.class))
        .orElse(null);
  }

  @Override
  public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest,
                                       HttpServletRequest request, HttpServletResponse response) {
      if (authorizationRequest == null){
        CookieUtils.deleteCookie(request,response, oauth2AuthorizationRequestCookieName);
        CookieUtils.deleteCookie(request,response,redirectUriParamCookieName);
        return;
      }
      CookieUtils.addCookie(response,oauth2AuthorizationRequestCookieName,CookieUtils.serialize(authorizationRequest),cookieExpiredSeconds);
      String redirectUriAfterLogin = request.getParameter(redirectUriParamCookieName);
      if (StringUtils.isNotBlank(redirectUriAfterLogin)){
        CookieUtils.addCookie(response,oauth2AuthorizationRequestCookieName, redirectUriAfterLogin,cookieExpiredSeconds);
      }

  }

  @Override
  public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
    return this.loadAuthorizationRequest(request);
  }
  public void removeAuthorizationRequestCookies(HttpServletRequest request,HttpServletResponse response){
    CookieUtils.deleteCookie(request,response,oauth2AuthorizationRequestCookieName);
    CookieUtils.deleteCookie(request,response,redirectUriParamCookieName);
  }
}
