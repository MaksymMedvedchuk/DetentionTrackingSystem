package com.arestmanagement.core.domain.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginDto {

	@Email
	private String email;

	private String password;
}
