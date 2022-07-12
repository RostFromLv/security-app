package com.learn.security;

import com.learn.model.UserDto;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtProvider implements Serializable {

  private static final long serialVersionUID = 3522491090480550716L;

  private final static String publicKeyFileName = "keys/public.pem";
  private final static String privateKeyFileName = "keys/private.der";


  private static RSAPrivateKey getPrivateKey(String fileName)
      throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    File file = new File(fileName);
    DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));

    byte[] keyBytes = new byte[(int) file.length()];
    dataInputStream.readFully(keyBytes);
    dataInputStream.close();

    PKCS8EncodedKeySpec spec  = new PKCS8EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");

    return (RSAPrivateKey) keyFactory.generatePrivate(spec);
  }

  private static PublicKey getPublicKey(String fileName)
      throws IOException, GeneralSecurityException {

    String publicKeyPem = getKey(fileName);
    publicKeyPem = publicKeyPem.replace("-----BEGIN PUBLIC KEY-----\n", "");
    publicKeyPem = publicKeyPem.replace("-----END PUBLIC KEY-----", "");
    byte[] keyBytes = Base64.decodeBase64(publicKeyPem);

    X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");

    return keyFactory.generatePublic(spec);
  }


  public String generateAccessToken(UserDto userDto) throws Exception {

    SignatureAlgorithm algorithm = SignatureAlgorithm.RS256;
     LocalDateTime now = LocalDateTime.now();
     Instant accessExpirationInstant = now.plusDays(1).atZone(ZoneId.systemDefault()).toInstant();
     Date accessExpiration = Date.from(accessExpirationInstant);
    return Jwts.builder()
        .setSubject(userDto.getEmail())
        .setExpiration(accessExpiration)
        .signWith(algorithm,getPrivateKey(privateKeyFileName))
        .claim("email",userDto.getEmail())
        .claim("password",userDto.getPassword())
        .compact();
  }

  public String generateRefreshToken(UserDto userDto)
      throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    LocalDateTime now = LocalDateTime.now();
    Instant refreshExpirationInstant = now.plusDays(25).atZone(ZoneId.systemDefault()).toInstant();
    Date refreshExpiration = Date.from(refreshExpirationInstant);
    return Jwts.builder()
        .setSubject(userDto.getEmail())
        .setExpiration(refreshExpiration)
        .signWith(getPrivateKey(privateKeyFileName))
        .compact();
  }

  public boolean validateAccessToken(String accessToken) {
    return validateToken(accessToken);
  }

  public boolean validateRefreshToken(String accessToken) {
    return validateToken(accessToken);
  }

  private boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(getPrivateKey(privateKeyFileName))
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (ExpiredJwtException expEx) {
      log.error("Token expired", expEx);
    } catch (UnsupportedJwtException unsEx) {
      log.error("Unsupported jwt", unsEx);
    } catch (MalformedJwtException mjEx) {
      log.error("Malformed jwt", mjEx);
    } catch (Exception e) {
      log.error("invalid token", e);
    }
    return false;
  }

  public KeyPair getKeyPair() throws GeneralSecurityException, IOException {
    return new KeyPair(getPublicKey(publicKeyFileName),getPrivateKey(privateKeyFileName));
  }

  public String getJWK() throws IOException, GeneralSecurityException {

    KeyPair keyPair = getKeyPair();

    JWK jwk = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
        .privateKey(keyPair.getPrivate())
        .keyUse(KeyUse.SIGNATURE)
        .keyID(UUID.randomUUID().toString())
        .build();
    return jwk.toJSONString();
  }


  public Claims getAccessClaims(String token)
      throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    return getClaims(token);
  }

  public Claims getRefreshClaims(String token)
      throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    return getClaims(token);
  }

  private Claims getClaims(String token)
      throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    return Jwts.parserBuilder()
        .setSigningKey(getPrivateKey(privateKeyFileName))
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  	private static String getKey(String filename) throws IOException {
	    String strKeyPEM = "";
	    BufferedReader br = new BufferedReader(new FileReader(filename));
	    String line;
	    while ((line = br.readLine()) != null) {
	        strKeyPEM += line + "\n";
	    }
	    br.close();
	    return strKeyPEM;
	}

}
