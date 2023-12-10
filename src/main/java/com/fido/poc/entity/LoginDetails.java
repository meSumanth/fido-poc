/**
 * 
 */
package com.fido.poc.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 
 * @author Sumanth
 * 
 */

@Data
@Entity
@Table(name = "LOGIN_DETAILS")
public class LoginDetails {

	@Id
	@Column(name = "id")
	private UUID id;

	@Column(name = "start_request")
	private String startRequest;

	@Column(name = "start_response")
	private String startResponse;

	@Column(name = "successful_login")
	private Boolean successfulLogin;

	@Column(name = "assertion_request")
	private String assertionRequest;

	@Column(name = "assertion_result")
	private String assertionResult;

}
