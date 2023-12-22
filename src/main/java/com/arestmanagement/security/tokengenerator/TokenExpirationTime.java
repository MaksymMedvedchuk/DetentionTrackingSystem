package com.arestmanagement.security.tokengenerator;

import com.arestmanagement.core.domain.enums.TokenType;

public interface TokenExpirationTime {

	long getActTimeToken(TokenType tokenType);
}
