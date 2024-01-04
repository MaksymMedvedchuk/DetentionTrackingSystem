package com.detentionsystem.security.tokengenerator;

import com.detentionsystem.core.domain.enums.TokenType;

public interface TokenExpirationTime {

	long getActTimeToken(TokenType tokenType);
}
