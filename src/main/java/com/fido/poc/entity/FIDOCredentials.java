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

@Entity
@Table(name = "FIDO_CRED_STORE")
@Data
public class FIDOCredentials {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "user_id")
	private UUID userId;

	@Column(name = "type")
	private String type;

	@Column(name = "public_key_cose")
	private String publicKeyCose;

}
