/**
 * 
 */
package com.fido.poc.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fido.poc.dto.SignUpFinishRequestDto;
import com.fido.poc.dto.SignUpFinishResponseDto;
import com.fido.poc.dto.SignUpStartRequestDto;
import com.fido.poc.dto.SignUpStartResponseDto;
import com.fido.poc.entity.FIDOCredentials;
import com.fido.poc.entity.User;
import com.fido.poc.service.SignUpService;
import com.fido.poc.service.UserService;
import com.fido.poc.util.YubicoUtils;
import com.yubico.webauthn.FinishRegistrationOptions;
import com.yubico.webauthn.RegistrationResult;
import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.StartRegistrationOptions;
import com.yubico.webauthn.data.AuthenticatorSelectionCriteria;
import com.yubico.webauthn.data.PublicKeyCredentialCreationOptions;
import com.yubico.webauthn.data.ResidentKeyRequirement;
import com.yubico.webauthn.data.UserIdentity;
import com.yubico.webauthn.data.UserVerificationRequirement;
import com.yubico.webauthn.exception.RegistrationFailedException;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Sumanth
 * 
 */

@Slf4j
@Service
public class SignUpServiceImpl implements SignUpService {

	@Autowired
	private UserService userService;

	@Autowired
	private RelyingParty relyingParty;

	@Override
	public SignUpStartResponseDto startSignUp(SignUpStartRequestDto signUpStartRequestDto) {

		User user = userService.findByEmail(signUpStartRequestDto.getEmail());

		if (Objects.isNull(user)) {
			log.info("Adding a user: {} since it doesnt exists", signUpStartRequestDto.getEmail());
			user = userService.saveUser(signUpStartRequestDto.getEmail());
		}

		PublicKeyCredentialCreationOptions options = createPublicKeyCredentialCreationOptions(user);
		SignUpStartResponseDto startResponse = createRegistrationStartResponse(options);

		return startResponse;
	}

	private PublicKeyCredentialCreationOptions createPublicKeyCredentialCreationOptions(User user) {
		UserIdentity userIdentity = UserIdentity.builder().name(user.getEmail()).displayName(user.getEmail())
				.id(YubicoUtils.toByteArray(user.getId())).build();

		AuthenticatorSelectionCriteria authenticatorSelectionCriteria = AuthenticatorSelectionCriteria.builder()
				.userVerification(UserVerificationRequirement.PREFERRED).residentKey(ResidentKeyRequirement.REQUIRED).build();

		StartRegistrationOptions startRegistrationOptions = StartRegistrationOptions.builder().user(userIdentity)
				.timeout(30_000).authenticatorSelection(authenticatorSelectionCriteria).build();

		PublicKeyCredentialCreationOptions options = this.relyingParty.startRegistration(startRegistrationOptions);

		return options;
	}

	private SignUpStartResponseDto createRegistrationStartResponse(PublicKeyCredentialCreationOptions options) {
		SignUpStartResponseDto startResponse = new SignUpStartResponseDto();
		startResponse.setCredentialCreationOptions(options);
		return startResponse;
	}

	@Override
	public SignUpFinishResponseDto finishSignUp(SignUpFinishRequestDto signUpFinishRequestDto,
			PublicKeyCredentialCreationOptions publicKeyCredentialCreationOptions) throws RegistrationFailedException {

		FinishRegistrationOptions options = FinishRegistrationOptions.builder()
				.request(publicKeyCredentialCreationOptions).response(signUpFinishRequestDto.getCredential()).build();
		RegistrationResult registrationResult = this.relyingParty.finishRegistration(options);

		FIDOCredentials fidoCredential = new FIDOCredentials();
		fidoCredential.setId(registrationResult.getKeyId().getId().getBase64Url());
		fidoCredential.setType(registrationResult.getKeyId().getType().name());
		fidoCredential.setUserId(YubicoUtils.toUUID(publicKeyCredentialCreationOptions.getUser().getId()));
		fidoCredential.setPublicKeyCose(registrationResult.getPublicKeyCose().getBase64Url());

		this.userService.addCredential(fidoCredential);

		SignUpFinishResponseDto signUpFinishResponseDto = new SignUpFinishResponseDto();
		signUpFinishResponseDto.setId(signUpFinishRequestDto.getId());
		signUpFinishResponseDto.setRegistrationComplete(true);

		return signUpFinishResponseDto;

	}

}
