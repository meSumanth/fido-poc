/**
 * 
 */
package com.fido.poc.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fido.poc.dto.SignUpFinishRequestDto;
import com.fido.poc.dto.SignUpFinishResponseDto;
import com.fido.poc.dto.SignUpStartRequestDto;
import com.fido.poc.dto.SignUpStartResponseDto;
import com.fido.poc.service.SignUpService;
import com.yubico.webauthn.exception.RegistrationFailedException;

import jakarta.servlet.http.HttpSession;


/**
 * 
 * @author Sumanth
 * 
 */

@RestController
public class SignUpController {
	
	private final String START_REG_REQUEST = "start_reg_request";
	
	@Autowired
	private SignUpService signUpService;
	
	
	@PostMapping("/signup/start")
	public SignUpStartResponseDto signUpStart(@RequestBody SignUpStartRequestDto signUpStartRequestDto, HttpSession httpSession) {
		 SignUpStartResponseDto responseDto = signUpService.startSignUp(signUpStartRequestDto);
		httpSession.setAttribute(START_REG_REQUEST, responseDto);
		return responseDto;
	}
	
	@PostMapping("/signup/finish")
	public SignUpFinishResponseDto signUpFinish(@RequestBody SignUpFinishRequestDto signUpFinishRequestDto, HttpSession httpSession) throws RegistrationFailedException {
		SignUpStartResponseDto signUpStartResponseDto = (SignUpStartResponseDto) httpSession.getAttribute(START_REG_REQUEST);
		
		if(Objects.isNull(signUpStartResponseDto)) {
			throw new RuntimeException("Cloud Not find the original request");
		}
		return signUpService.finishSignUp(signUpFinishRequestDto, signUpStartResponseDto.getCredentialCreationOptions());
	}

}
