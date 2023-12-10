/**
 * 
 */
package com.fido.poc.service;

import java.util.Optional;
import java.util.UUID;

import com.fido.poc.entity.FIDOCredentials;
import com.fido.poc.entity.User;

/**
 * 
 * @author Sumanth
 * 
 */


public interface UserService {

	User findByEmail(String email);
	
	User saveUser(String email);
	
	void addCredential(FIDOCredentials fidoCredentials);
	
	Optional<User> findUserById(UUID userId);
	
	Optional<FIDOCredentials> findCredentialById(String credentialId);
	
}
