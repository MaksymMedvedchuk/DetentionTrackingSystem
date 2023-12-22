package com.arestmanagement.security.service;

import com.arestmanagement.core.domain.entity.Token;
import com.arestmanagement.core.domain.enums.TokenType;

public interface TokenService {

	Token findToken(String tokenValue, TokenType tokenType);

	Long findUserId(String tokenValue, TokenType tokenType);

	void deleteAllTokensByUserId(Long userId);

	Token saveToken(Token token);

	String findTokenValue(Long userId, TokenType tokenType);

	boolean validateToken(Token token);
}
