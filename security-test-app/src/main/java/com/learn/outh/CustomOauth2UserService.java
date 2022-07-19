package com.learn.outh;

import com.learn.domain.AuthenticatorProvider;
import com.learn.domain.User;
import com.learn.outh.user.CustomOauth2User;
import com.learn.outh.user.Oauth2UserFactory;
import com.learn.service.repository.UserRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService {


  private final UserRepository userRepository;

  @Autowired
  public CustomOauth2UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

    OAuth2User user = super.loadUser(userRequest);

    try {
      return processOauth2ser(userRequest, user);
    } catch (AuthenticationException e) {
      throw e;
    } catch (Exception e) {
      throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
    }
  }

  private OAuth2User processOauth2ser(OAuth2UserRequest request, OAuth2User oAuth2User) {
    String registrationId = request.getClientRegistration().getRegistrationId();
    CustomOauth2User customOauth2User = Oauth2UserFactory.getOauth2User(registrationId, oAuth2User.getAttributes());

    if (customOauth2User.getEmail() == null || customOauth2User.getEmail().isEmpty()) {
      throw new OAuth2AuthenticationException("Provider email not found(" + registrationId + ")");
    }

    Optional<User> userOptional = userRepository.findByEmail(customOauth2User.getEmail());
    User user;

    if (userOptional.isPresent()) {
      user = userOptional.get();
      if (!user.getProvider().equals(
          AuthenticatorProvider.valueOf(request.getClientRegistration().getRegistrationId()))) {
        throw new OAuth2AuthenticationException(
            "You are signed up with provider:" + user.getProvider() + ". Please use your: " +
                user.getProvider() + " provider to login");
      }
      user = updateExistUser(user, customOauth2User);
    } else {
      user = registerNewUser(request, customOauth2User);
    }
    return UserPrincipal.create(user, customOauth2User.getAttributes());
  }

  private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, CustomOauth2User oauth2User) {
    User user = new User();
    String[] userNameCredentials = splitUserNameCredentials(oauth2User.getName());

    user.setProvider(AuthenticatorProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
    user.setProviderId(oauth2User.getId());
    user.setFirstName(userNameCredentials[0]);
    user.setLastName(userNameCredentials[1]);
    user.setEmail(oauth2User.getEmail());
    log.debug("Registered new user:" + user);
    return userRepository.save(user);
  }

  private User updateExistUser(User existingUser,CustomOauth2User customOauth2User){
    String[] userNameCredentials = splitUserNameCredentials(customOauth2User.getName());

    existingUser.setFirstName(userNameCredentials[0]);
    existingUser.setLastName(userNameCredentials[1]);
    log.debug("Updated user:"+existingUser);
    return userRepository.save(existingUser);
  }


  private String[] splitUserNameCredentials(String userName){
    String[] userNameCredentials = userName.split(" ");

    String userFirstName;
    String userLastname;

    if (userNameCredentials.length > 1) {
      userFirstName = userNameCredentials[0];
      userLastname = userNameCredentials[1];
    } else {
      userFirstName = userName;
      userLastname = "";
    }
    return new String[] {userFirstName, userLastname};
  }
}
