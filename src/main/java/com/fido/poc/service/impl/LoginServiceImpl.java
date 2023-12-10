/**
 * 
 */
package com.fido.poc.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fido.poc.dao.LoginDetailsRepository;
import com.fido.poc.dto.LoginFinishRequestDto;
import com.fido.poc.dto.LoginStartRequestDto;
import com.fido.poc.dto.LoginStartResponseDto;
import com.fido.poc.entity.LoginDetails;
import com.fido.poc.entity.User;
import com.fido.poc.service.LoginService;
import com.fido.poc.service.UserService;
import com.fido.poc.util.JsonUtils;
import com.yubico.webauthn.AssertionRequest;
import com.yubico.webauthn.AssertionResult;
import com.yubico.webauthn.FinishAssertionOptions;
import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.StartAssertionOptions;
import com.yubico.webauthn.exception.AssertionFailedException;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Sumanth
 * 
 */

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private RelyingParty relyingParty;

	@Autowired
	private UserService userService;

	@Autowired
	private LoginDetailsRepository loginDetailsRepository;

	@Override
	public LoginStartResponseDto loginStart(LoginStartRequestDto loginStartRequestDto) {

		// Find the user in the user database
		User user = userService.findByEmail(loginStartRequestDto.getEmail());

		// make the assertion request to send to the client
		StartAssertionOptions options = StartAssertionOptions.builder().timeout(60_000)
				.username(loginStartRequestDto.getEmail()).build();
		AssertionRequest assertionRequest = this.relyingParty.startAssertion(options);

		LoginStartResponseDto loginStartResponse = new LoginStartResponseDto();
		loginStartResponse.setId(UUID.randomUUID());
		loginStartResponse.setAssertionRequest(assertionRequest);

		LoginDetails loginDetails = new LoginDetails();
		loginDetails.setId(loginStartResponse.getId());
		loginDetails.setStartRequest(JsonUtils.toJson(loginStartRequestDto));
		loginDetails.setStartResponse(JsonUtils.toJson(loginStartResponse));
		try {
			loginDetails.setAssertionRequest(assertionRequest.toJson());
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		loginDetailsRepository.save(loginDetails);

		return loginStartResponse;
	}

	@Override
	public AssertionResult finishLogin(LoginFinishRequestDto request) throws AssertionFailedException {
		LoginDetails loginDetails =
		        this.loginDetailsRepository
		            .findById(request.getId())
		            .orElseThrow(
		                () ->
		                    new RuntimeException(
		                        "flow id " + request.getId() + " not found"));

		    var assertionRequestJson = loginDetails.getAssertionRequest();
		    AssertionRequest assertionRequest = null;
		    try {
		      assertionRequest = AssertionRequest.fromJson(assertionRequestJson);
		    } catch (JsonProcessingException e) {
		      throw new IllegalArgumentException("Cloud not deserialize the assertion Request");
		    }

		    FinishAssertionOptions options =
		        FinishAssertionOptions.builder()
		            .request(assertionRequest)
		            .response(request.getCredential())
		            .build();

		    AssertionResult assertionResult = relyingParty.finishAssertion(options);

		    loginDetails.setAssertionResult(JsonUtils.toJson(assertionResult));
		    loginDetails.setSuccessfulLogin(assertionResult.isSuccess());

		    return assertionResult;
	}

}
