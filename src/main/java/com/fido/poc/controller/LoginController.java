/**
 * 
 */
package com.fido.poc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fido.poc.dto.LoginFinishRequestDto;
import com.fido.poc.dto.LoginStartRequestDto;
import com.fido.poc.dto.LoginStartResponseDto;
import com.fido.poc.service.LoginService;
import com.yubico.webauthn.AssertionRequest;
import com.yubico.webauthn.AssertionResult;
import com.yubico.webauthn.exception.AssertionFailedException;

import jakarta.servlet.http.HttpSession;

/**
 * 
 * @author Sumanth
 * 
 */
@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;

	private final String START_REG_REQUEST = "start_login_request";

	@PostMapping("/webauthn/login/start")
	public LoginStartResponseDto loginStart(@RequestBody LoginStartRequestDto loginStartRequestDto,
			HttpSession httpSession) {
		LoginStartResponseDto loginStartResponseDto = loginService.loginStart(loginStartRequestDto);
		httpSession.setAttribute(START_REG_REQUEST, loginStartResponseDto);
		return loginStartResponseDto;
	}

	@ResponseBody
	@PostMapping("/webauthn/login/finish")
	AssertionResult loginStartResponse(@RequestBody LoginFinishRequestDto request, HttpSession session)
			throws AssertionFailedException {
		AssertionRequest assertionRequest = (AssertionRequest) session.getAttribute(START_REG_REQUEST);
		if (assertionRequest == null) {
			throw new RuntimeException("Cloud Not find the original request");
		}

		AssertionResult result = this.loginService.finishLogin(request);
		if (result.isSuccess()) {
			session.setAttribute(AssertionRequest.class.getName(), result);
		}
		return result;
	}
	

}
