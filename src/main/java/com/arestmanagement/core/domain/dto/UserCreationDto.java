package com.arestmanagement.core.domain.dto;

import com.arestmanagement.core.domain.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

@Data
public class UserCreationDto {

	@Email
	private String email;
}
