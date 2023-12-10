/**
 * 
 */
package com.fido.poc.service;

import com.fido.poc.dto.LoginFinishRequestDto;
import com.fido.poc.dto.LoginStartRequestDto;
import com.fido.poc.dto.LoginStartResponseDto;
import com.yubico.webauthn.AssertionResult;
import com.yubico.webauthn.exception.AssertionFailedException;

/**
 * 
 * @author Sumanth
 * 
 */
public interface LoginService {

	LoginStartResponseDto loginStart(LoginStartRequestDto loginStartRequestDto);

	AssertionResult finishLogin(LoginFinishRequestDto request) throws AssertionFailedException;

}
