package com.learn.security;


import com.learn.model.UserDto;
import com.learn.service.UserService;
import io.jsonwebtoken.Claims;
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final UserService userService;
  private final Map<String,String> refreshStorage = new HashMap<>();
  private final JwtProvider jwtProvider;

  @Autowired
  public AuthService(UserService userService, JwtProvider jwtProvider) {
    this.userService = userService;
    this.jwtProvider = jwtProvider;
  }

  public  JwtResponse login(JwtRequest jwtRequest) throws AuthException {
    System.out.println(jwtRequest);
    System.out.println(jwtRequest.getEmail());
    final UserDto user = userService.findUserByEmail(jwtRequest.getEmail());
    if (user.getPassword().equals(jwtRequest.getPassword())){
      final String accessToken = jwtProvider.generateAccessToken(user);
      final String refreshToken = jwtProvider.generateRefreshToken(user);
      refreshStorage.put(user.getEmail(), refreshToken);
      return  new JwtResponse(accessToken,refreshToken);
    }else {
      throw  new AuthException("Wrong credential(pass)");
    }
  }

  public JwtResponse getAccessToken(String refreshToken){
    if (jwtProvider.validateRefreshToken(refreshToken)){
      Claims claims = jwtProvider.getRefreshClaims(refreshToken);
      String email = claims.getSubject();
      String saveRefreshToke = refreshStorage.get(email);
      if(saveRefreshToke!= null && saveRefreshToke.equals(refreshToken)){
        UserDto user = userService.findUserByEmail(email);
        String accessToken = jwtProvider.generateAccessToken(user);
        return new JwtResponse(accessToken,null);
      }
    }
    return new JwtResponse(null ,null);
  }
  public JwtResponse refresh(String refreshToken) throws AuthException {
    if (jwtProvider.validateRefreshToken(refreshToken)){
      Claims claims = jwtProvider.getAccessClaims(refreshToken);
      String email = claims.getSubject();
      String saveRefreshToken = refreshStorage.get(email);
      if (saveRefreshToken!=null && saveRefreshToken.equals(refreshToken)){
        UserDto user = userService.findUserByEmail(email);
        String accessToken = jwtProvider.generateAccessToken(user);
        String newRefreshToken = jwtProvider.generateRefreshToken(user);
        refreshStorage.put(user.getEmail(),newRefreshToken);
        return  new JwtResponse(accessToken,newRefreshToken);
      }
    }
    throw new AuthException("Wrong  JW-TOKEN");
  }



}