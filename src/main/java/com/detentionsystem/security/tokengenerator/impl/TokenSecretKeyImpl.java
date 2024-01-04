package com.detentionsystem.security.tokengenerator.impl;

import com.detentionsystem.core.domain.enums.TokenType;
import com.detentionsystem.security.tokengenerator.TokenSecretKey;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.EnumMap;
import java.util.Map;

@Service
public class TokenSecretKeyImpl implements TokenSecretKey {

	private final Map<TokenType, Key> typeKey = new EnumMap<>(TokenType.class);

	private final String accessTokenKey;

	private final String validationTokenKey;

	private final String refreshTokenKey;

	public TokenSecretKeyImpl(
		@Value("${security.jwt.access.token.secret-key}") final String accessTokenKey,
		@Value("${security.jwt.validation.token.secret-key}") final String validationTokenKey,
		@Value("${security.jwt.refresh.token.secret-key}") final String refreshTokenKey
	) {
		this.accessTokenKey = accessTokenKey;
		this.validationTokenKey = validationTokenKey;
		this.refreshTokenKey = refreshTokenKey;
	}

	@PostConstruct
	protected void init() {
		typeKey.put(TokenType.ACCESS, convetStringToKey(accessTokenKey));
		typeKey.put(TokenType.VERIFICATION, convetStringToKey(validationTokenKey));
		typeKey.put(TokenType.REFRESH, convetStringToKey(refreshTokenKey));
	}

	private Key convetStringToKey(String base64Key) {
		byte[] keyBytes = Base64.getDecoder().decode(base64Key.getBytes(StandardCharsets.UTF_8));
		return Keys.hmacShaKeyFor(keyBytes);
	}

	@Override
	public Key getTokenKey(final TokenType tokenType) {
		return typeKey.get(tokenType);
	}
}
