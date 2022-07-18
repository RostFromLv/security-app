package com.learn.outh;

import com.learn.domain.AuthenticatorProvider;
import com.learn.model.SecurityUserDto;
import com.learn.model.UserDto;
import com.learn.outh.user.CustomOauth2User;
import com.learn.service.SecurityUserService;
import com.learn.service.UserService;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class Oauth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final UserService userService;
  private final SecurityUserService securityUserService;

  @Autowired
  public Oauth2LoginSuccessHandler(UserService userService,
                                   SecurityUserService securityUserService) {
    this.userService = userService;
    this.securityUserService = securityUserService;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication)
      throws IOException, ServletException {

    AuthenticatorProvider provider = determineProvider(request);
    Assert.notNull(provider,"Unknown provider!");

    CustomOauth2User oauth2User = (CustomOauth2User) authentication.getPrincipal();
    System.err.println(((CustomOauth2User) authentication.getPrincipal()).getAttributes());

    String principalName = oauth2User.getPrincipalName();
    String email = oauth2User.getEmail();
    String name = oauth2User.getName();

    String lastName = splitNameToFirstNameAndLastName(name)[1];
    name = splitNameToFirstNameAndLastName(name)[0];

    UserDto dbUser = userService.findUserByEmail(email);

    System.out.println(principalName);
    if (dbUser == null && !securityUserService.existByUserPrincipalName(principalName)){
      UserDto createdUser = userService.createUserAfterSuccessOauthLogin(name,email,lastName,provider);
      securityUserService.create(createSecurityDto(principalName,createdUser.getId(),provider));
    }else {
      UserDto target = new UserDto(null,email,null,name,lastName);

      if (userService.userWasChanged(dbUser,target)){
        userService.update(target, dbUser.getId());
      }
    }

    super.onAuthenticationSuccess(request, response, authentication);
  }

  private SecurityUserDto createSecurityDto(String principalName,Integer userId,AuthenticatorProvider providerCode){
    SecurityUserDto securityUserDto = new SecurityUserDto();
    securityUserDto.setUserId(userId);
    securityUserDto.setPrincipalName(principalName);
    securityUserDto.setAuthProvider(providerCode);
    return securityUserDto;
  }

  private AuthenticatorProvider determineProvider(HttpServletRequest request){
    String requestUrl = request.getServletPath().toLowerCase(Locale.ROOT);
    if (requestUrl.contains("github")){
      return AuthenticatorProvider.GITHUB;
    }else if (requestUrl.contains("google")){
      return AuthenticatorProvider.GOOGLE;
    }else if (requestUrl.contains("facebook")){
      return AuthenticatorProvider.FACEBOOK;
    }
    return null;
  }

  private String[] splitNameToFirstNameAndLastName(String name) {
    return name.split(" ");
  }

}
