package com.arestmanagement.security.tokengenerator;

import com.arestmanagement.core.domain.enums.TokenType;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public interface TokenSecretKey {

	Key getTokenKey(TokenType tokenType);
}
