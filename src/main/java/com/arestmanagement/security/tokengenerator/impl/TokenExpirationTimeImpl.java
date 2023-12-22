package com.arestmanagement.security.tokengenerator.impl;

import com.arestmanagement.core.domain.enums.TokenType;
import com.arestmanagement.security.tokengenerator.TokenExpirationTime;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class TokenExpirationTimeImpl implements TokenExpirationTime {

	private final Long actVarificationToken;

	private final Long actAccessToken;

	private final Long actRefreshToken;

	private final Map<TokenType, Long> actTine = new EnumMap<>(TokenType.class);


	private TokenExpirationTimeImpl(
		@Value("${security.jwt.validation.token.expire-length}") final Long actVarificationToken,
		@Value("${security.jwt.access.token.expire-length}") final Long actAccessToken,
		@Value("${security.jwt.refresh.token.expire-length}") final Long actRefreshToken
	) {
		this.actVarificationToken = actVarificationToken;
		this.actAccessToken = actAccessToken;
		this.actRefreshToken = actRefreshToken;
	}

	@PostConstruct
	void init() {
		actTine.put(TokenType.VERIFICATION, actVarificationToken);
		actTine.put(TokenType.ACCESS, actAccessToken);
		actTine.put(TokenType.REFRESH, actRefreshToken);
	}

	@Override
	public long getActTimeToken(final TokenType tokenType) {
		return actTine.get(tokenType);
	}
}
