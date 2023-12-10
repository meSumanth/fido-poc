/**
 * 
 */
package com.fido.poc.dto;

import java.util.UUID;

import com.yubico.webauthn.AssertionRequest;

import lombok.Data;

/**
 * 
 * @author Sumanth
 * 
 */

@Data
public class LoginStartResponseDto {

	private UUID id;
	
	private AssertionRequest assertionRequest;
}
