package com.fido.poc.login;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationConverter;

import com.fido.poc.dto.LoginFinishRequestDto;
import com.fido.poc.util.JsonUtils;

public class FidoAuthenticationConverter implements AuthenticationConverter {
  @Override
  public Authentication convert(HttpServletRequest request) {
    String username = request.getParameter("username");
    if (username == null || username.isBlank()) {
      throw new UsernameNotFoundException("login request does not contain a username");
    }

    String finishRequest = request.getParameter("finishRequest");
    if (finishRequest == null || finishRequest.isBlank()) {
      throw new BadCredentialsException("fido credentials missing");
    }
    LoginFinishRequestDto loginFinishRequest = JsonUtils.fromJson(finishRequest, LoginFinishRequestDto.class);
    return new FidoAuthenticationToken(username, loginFinishRequest);
  }
}
