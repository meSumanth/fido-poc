/**
 * 
 */
package com.fido.poc.dao;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fido.poc.entity.FIDOCredentials;
import com.fido.poc.entity.User;
import com.fido.poc.service.UserService;
import com.fido.poc.util.YubicoUtils;
import com.yubico.webauthn.CredentialRepository;
import com.yubico.webauthn.RegisteredCredential;
import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.PublicKeyCredentialDescriptor;
import com.yubico.webauthn.data.PublicKeyCredentialType;
import com.yubico.webauthn.data.exception.Base64UrlException;

/**
 * 
 * @author Sumanth
 * 
 */

@Repository
public class CredentialRepositoryImpl implements CredentialRepository {

	@Autowired
	private UserService userService;

	@Override
	public Set<PublicKeyCredentialDescriptor> getCredentialIdsForUsername(String email) {

		User user = userService.findByEmail(email);

		if (Objects.nonNull(user) && (user.getCredentials() != null && !user.getCredentials().isEmpty())) {
			return user.getCredentials().stream().map(CredentialRepositoryImpl::toPublicKeyCredentialDescriptor)
					.collect(Collectors.toSet());
		}
		return Set.of();
	}

	@Override
	public Optional<ByteArray> getUserHandleForUsername(String username) {
		User user = userService.findByEmail(username);

		if (Objects.nonNull(user)) {
			return Optional.of(YubicoUtils.toByteArray(user.getId()));
		}
		return Optional.of(null);
	}

	@Override
	public Optional<String> getUsernameForUserHandle(ByteArray userHandle) {
		if (userHandle.isEmpty()) {
			return Optional.empty();
		}
		Optional<String> user = userService.findUserById(YubicoUtils.toUUID(userHandle))
				.map(userAccount -> userAccount.getEmail());
		return user;
	}

	@Override
	public Optional<RegisteredCredential> lookup(ByteArray credentialId, ByteArray userHandle) {
		var result = this.userService.findUserById(YubicoUtils.toUUID(userHandle)).map(user -> user.getCredentials())
				.orElse(Set.of()).stream().filter(cred -> {
					try {
						return credentialId.equals(ByteArray.fromBase64Url(cred.getId()));
					} catch (Base64UrlException e) {
						throw new RuntimeException(e);
					}
				}).findFirst().map(CredentialRepositoryImpl::toRegisteredCredential);

		return result;
	}

	@Override
	public Set<RegisteredCredential> lookupAll(ByteArray credentialId) {
		var result = this.userService.findCredentialById(credentialId.getBase64Url())
				.map(CredentialRepositoryImpl::toRegisteredCredential).map(r -> Set.of(r)).orElse(Set.of());

		return result;
	}

	private static PublicKeyCredentialDescriptor toPublicKeyCredentialDescriptor(FIDOCredentials cred) {
		try {
			return PublicKeyCredentialDescriptor.builder().id(ByteArray.fromBase64Url(cred.getId()))
					.type(PublicKeyCredentialType.valueOf(cred.getType())).build();

		} catch (Base64UrlException e) {
			throw new RuntimeException(e);
		}
	}

	private static RegisteredCredential toRegisteredCredential(FIDOCredentials fidoCredential) {
		try {
			return RegisteredCredential.builder().credentialId(ByteArray.fromBase64Url(fidoCredential.getId()))
					.userHandle(YubicoUtils.toByteArray(fidoCredential.getUserId()))
					.publicKeyCose(ByteArray.fromBase64Url(fidoCredential.getPublicKeyCose())).build();
		} catch (Base64UrlException e) {
			throw new RuntimeException(e);
		}
	}

}
