/**
 * 
 */
package com.fido.poc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.yubico.webauthn.CredentialRepository;
import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.data.RelyingPartyIdentity;

/**
 * 
 */

@Component
public class RelyingPartyConfig {

	@Bean
	RelyingParty relyingParty(CredentialRepository credentialRepository) {
		RelyingPartyIdentity rpIdentity = RelyingPartyIdentity.builder().id("localhost")
				.name("IAM FIDO Demo Application").build();

		var relyingParty = RelyingParty.builder().identity(rpIdentity).credentialRepository(credentialRepository)
				.allowOriginPort(true).build();

		return relyingParty;
	}

}
