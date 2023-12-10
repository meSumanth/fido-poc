/**
 * 
 */
package com.fido.poc.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fido.poc.entity.User;

/**
 * 
 */
public interface UserRepository extends JpaRepository<User, UUID>{
	
	Optional<User> findByEmail(String email);
	
	User save(User user);
}
