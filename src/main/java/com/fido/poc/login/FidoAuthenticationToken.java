package com.fido.poc.login;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import com.fido.poc.dto.LoginFinishRequestDto;

public class FidoAuthenticationToken extends AbstractAuthenticationToken {
  private final String username;
  private final LoginFinishRequestDto loginFinishRequest;

  public FidoAuthenticationToken(String username, LoginFinishRequestDto loginFinishRequest) {
    super(null);
    this.username = username;
    this.loginFinishRequest = loginFinishRequest;
  }

  public String getUsername() {
    return username;
  }

  public LoginFinishRequestDto getLoginFinishRequest() {
    return loginFinishRequest;
  }

  @Override
  public Object getCredentials() {
    return loginFinishRequest;
  }

  @Override
  public Object getPrincipal() {
    return username;
  }
}
