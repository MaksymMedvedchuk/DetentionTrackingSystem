package com.detentionsystem.core.domain.dto;

import com.detentionsystem.core.domain.enums.TokenType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TokenDto {

	private Long id;
	private String tokenValue;
	private TokenType tokenType;
	private LocalDateTime expirationDate;
	private Long userId;
}
