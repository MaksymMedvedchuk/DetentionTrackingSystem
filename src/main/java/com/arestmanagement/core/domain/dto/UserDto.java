package com.arestmanagement.core.domain.dto;

import com.arestmanagement.core.domain.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserDto {
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long id;
	private String firstName;
	private String lastName;
	@Email
	private String email;
	private String password;
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Role role;
	/*@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private boolean isVerified;*/
}
