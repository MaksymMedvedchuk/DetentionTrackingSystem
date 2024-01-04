package com.detentionsystem.core.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

@Data
public class UserCreationDto {

	@Email
	private String email;
}
