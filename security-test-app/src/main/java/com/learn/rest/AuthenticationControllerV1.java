package com.learn.rest;

import com.learn.security.AuthService;
import com.learn.security.JwtRequest;
import com.learn.security.JwtResponse;
import com.learn.security.RefreshJwtRequest;
import javax.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationControllerV1 {

  private final AuthService authService;


  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public JwtResponse login(@RequestBody JwtRequest jwtRequest) throws AuthException {

    return authService.login(jwtRequest);
  }

  @PostMapping("/token")
  @ResponseStatus(HttpStatus.OK)
  public JwtResponse getNewAccessToken(@RequestBody RefreshJwtRequest request){
    return authService.getAccessToken(request.getRefreshToken());
  }
}
