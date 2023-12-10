/**
 * 
 */
package com.fido.poc.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yubico.webauthn.data.PublicKeyCredentialCreationOptions;

import lombok.Data;

/**
 * 
 * @author Sumanth
 * 
 */


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignUpStartResponseDto {

	private UUID id;
	
	private PublicKeyCredentialCreationOptions credentialCreationOptions;
}
