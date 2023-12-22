package com.arestmanagement.core.domain.dto;

import com.arestmanagement.core.domain.entity.User;
import com.arestmanagement.core.domain.enums.TokenType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
public class TokenDto {

	private Long id;
	private String tokenValue;
	private TokenType tokenType;
	private LocalDateTime expirationDate;
	private Long userId;
}
