package com.detentionsystem.core.domain.dto;

import com.detentionsystem.core.domain.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
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
}
