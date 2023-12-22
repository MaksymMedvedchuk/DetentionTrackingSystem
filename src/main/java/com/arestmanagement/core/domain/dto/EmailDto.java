package com.arestmanagement.core.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class EmailDto {
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long id;
	@Email
	private String email;
}
