package com.detentionsystem.security.tokengenerator;

import com.detentionsystem.core.domain.enums.TokenType;

import java.security.Key;

public interface TokenSecretKey {

	Key getTokenKey(TokenType tokenType);
}
