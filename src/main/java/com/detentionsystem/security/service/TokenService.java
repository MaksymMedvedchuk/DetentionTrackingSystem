package com.detentionsystem.security.service;

import com.detentionsystem.core.domain.entity.Token;
import com.detentionsystem.core.domain.enums.TokenType;

public interface TokenService {

	Token findToken(String tokenValue, TokenType tokenType);

	Long findUserId(String tokenValue, TokenType tokenType);

	void deleteAllTokensByUserId(Long userId);

	Token saveToken(Token token);

	String findTokenValue(Long userId, TokenType tokenType);

	boolean validateToken(Token token);
}
