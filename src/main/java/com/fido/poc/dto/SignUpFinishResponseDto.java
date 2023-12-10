/**
 * 
 */
package com.fido.poc.dto;

import java.util.UUID;

import lombok.Data;

/**
 * 
 * @author Sumanth
 * 
 */

@Data
public class SignUpFinishResponseDto {

	private UUID id;

	private boolean registrationComplete;
}
