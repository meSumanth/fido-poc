/**
 * 
 */
package com.fido.poc.dto;

import java.util.UUID;

import com.yubico.webauthn.data.AuthenticatorAssertionResponse;
import com.yubico.webauthn.data.ClientAssertionExtensionOutputs;
import com.yubico.webauthn.data.PublicKeyCredential;

import lombok.Data;

/**
 * 
 * @author Sumanth
 */

@Data
public class LoginFinishRequestDto {

	private UUID id;

	private PublicKeyCredential<AuthenticatorAssertionResponse, ClientAssertionExtensionOutputs> credential;
}
