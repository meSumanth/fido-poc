/**
 * 
 */
package com.fido.poc.service;

import com.fido.poc.dto.SignUpFinishRequestDto;
import com.fido.poc.dto.SignUpFinishResponseDto;
import com.fido.poc.dto.SignUpStartRequestDto;
import com.fido.poc.dto.SignUpStartResponseDto;
import com.yubico.webauthn.data.PublicKeyCredentialCreationOptions;
import com.yubico.webauthn.exception.RegistrationFailedException;

/**
 * 
 * @author Sumanth
 * 
 */

public interface SignUpService {
	
	/**
	 * 
	 * @param signUpStartRequestDto
	 * @return
	 */
	public SignUpStartResponseDto startSignUp(SignUpStartRequestDto signUpStartRequestDto);
	
	
	/**
	 * 
	 * @param signUpFinishRequestDto
	 * @param publicKeyCredentialCreationOptions 
	 * @return
	 * @throws RegistrationFailedException 
	 */
	public SignUpFinishResponseDto finishSignUp(SignUpFinishRequestDto signUpFinishRequestDto, PublicKeyCredentialCreationOptions publicKeyCredentialCreationOptions) throws RegistrationFailedException;

}
