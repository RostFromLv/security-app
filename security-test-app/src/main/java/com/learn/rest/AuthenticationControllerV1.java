package com.learn.rest;

import com.learn.security.AuthService;
import com.learn.security.JwtRequest;
import com.learn.security.JwtResponse;
import com.learn.security.RefreshJwtRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationControllerV1 {

  @Autowired
  private final AuthService authService;

  public AuthenticationControllerV1(AuthService authService) {
    this.authService = authService;
  }


  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public JwtResponse login(  @RequestBody JwtRequest jwtRequest) throws Exception {
    return authService.login(jwtRequest);
  }

  @PostMapping("/token")
  @ResponseStatus(HttpStatus.OK)
  public JwtResponse getNewAccessToken(@RequestBody RefreshJwtRequest request) throws Exception {
    return authService.getAccessToken(request.getRefreshToken());
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/public/keys")
  @ResponseStatus(HttpStatus.OK)
//  public Collection<String> getPublicKeys() throws GeneralSecurityException, IOException {
  public String getPublicKeys() throws GeneralSecurityException, IOException {

    return  authService.getAllJWK().toString();
  }
}
