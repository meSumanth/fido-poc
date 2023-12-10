/**
 * 
 */
package com.fido.poc.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fido.poc.entity.LoginDetails;

/**
 * 
 * @author Sumanth
 * 
 */

@Repository
public interface LoginDetailsRepository extends JpaRepository<LoginDetails, UUID>{
	
	Optional<LoginDetails> findLoginDetailsById(UUID id);

}
