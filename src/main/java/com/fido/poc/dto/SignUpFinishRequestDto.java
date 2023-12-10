/**
 * 
 */
package com.fido.poc.dto;

import java.util.UUID;

import com.yubico.webauthn.data.AuthenticatorAttestationResponse;
import com.yubico.webauthn.data.ClientRegistrationExtensionOutputs;
import com.yubico.webauthn.data.PublicKeyCredential;

import lombok.Data;

/**
 * 
 * @author Sumanth
 * 
 */

@Data
public class SignUpFinishRequestDto {

	private UUID id;
	
	private PublicKeyCredential<AuthenticatorAttestationResponse, ClientRegistrationExtensionOutputs> credential;

}
