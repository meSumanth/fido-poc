package com.fido.poc.login;

import java.util.Set;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import com.fido.poc.dto.LoginFinishRequestDto;

public class FidoAuthentication extends AbstractAuthenticationToken {
  private final String username;
  private final LoginFinishRequestDto loginFinishRequestDto;
  private final String assertionResultJson;

  public FidoAuthentication(
      FidoAuthenticationToken fidoAuthenticationToken, String assertionResultJson) {
    super(Set.of());
    this.username = fidoAuthenticationToken.getUsername();
    this.loginFinishRequestDto = fidoAuthenticationToken.getLoginFinishRequest();
    this.assertionResultJson = assertionResultJson;
    this.setAuthenticated(true);
  }

  public String getUsername() {
    return username;
  }

  public LoginFinishRequestDto getLoginFinishRequest() {
    return loginFinishRequestDto;
  }

  @Override
  public Object getCredentials() {
    return loginFinishRequestDto;
  }

  @Override
  public Object getPrincipal() {
    return username;
  }

  public String getAssertionResultJson() {
    return assertionResultJson;
  }
}
