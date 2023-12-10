/**
 * 
 */
package com.fido.poc.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fido.poc.dao.UserRepository;
import com.fido.poc.entity.FIDOCredentials;
import com.fido.poc.entity.User;
import com.fido.poc.service.UserService;

/**
 * 
 * @author Sumanth
 * 
 */

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User findByEmail(String email) {
		Optional<User> userOptional = userRepository.findByEmail(email);
		if (userOptional.isPresent())
			return userOptional.get();
		return null;
	}

	@Override
	public User saveUser(String email) {
		User user = new User();
		user.setEmail(email);
		return userRepository.save(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void addCredential(FIDOCredentials fidoCredential) {
		FIDOCredentials fidoCredentialEntity = new FIDOCredentials();
		fidoCredentialEntity.setUserId(fidoCredential.getUserId());
		fidoCredentialEntity.setType(fidoCredential.getType());
		fidoCredentialEntity.setPublicKeyCose(fidoCredential.getPublicKeyCose());
		fidoCredentialEntity.setId(fidoCredential.getId());

		User account = this.userRepository.findById(fidoCredential.getUserId())
				.orElseThrow(() -> new RuntimeException("can't add a credential to a user that does not exist"));
		account.getCredentials().add(fidoCredentialEntity);
	}

	@Override
	public Optional<User> findUserById(UUID userId) {
		return userRepository.findById(userId);
	}
	
	@Override
	  public Optional<FIDOCredentials> findCredentialById(String credentialId) {
	    return Optional.empty();
	  }

}
