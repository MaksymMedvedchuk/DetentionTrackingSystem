package com.arestmanagement.security.service.impl;

import com.arestmanagement.core.domain.entity.Token;
import com.arestmanagement.core.domain.entity.User;
import com.arestmanagement.core.domain.enums.TokenType;
import com.arestmanagement.core.service.DataTimeService;
import com.arestmanagement.security.model.AuthUser;
import com.arestmanagement.security.service.RefreshTokenService;
import com.arestmanagement.security.service.TokenService;
import com.arestmanagement.security.tokengenerator.TokenExpirationTime;
import com.arestmanagement.security.tokengenerator.TokenGeneratorProvider;
import com.arestmanagement.security.tokengenerator.TokenSecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RefreshTokenServiceImpl extends TokenGeneratorProvider implements RefreshTokenService {

	private final TokenService tokenService;

	public RefreshTokenServiceImpl(
		final TokenService tokenService,
		final TokenExpirationTime tokenExpirationTime,
		final DataTimeService dataTimeService,
		final TokenSecretKey tokenSecretKey

	) {
		super(tokenExpirationTime, dataTimeService, tokenSecretKey);
		this.tokenService = tokenService;
	}

	@Override
	public Token generateRefreshToken(final AuthUser authUser) {
		final Token generatedToken = generateToken(authUser, TokenType.REFRESH);
		tokenService.saveToken(generatedToken);
		log.debug(
			"In generateRefreshToken: successfully create VerificationToke for user with id: {}",
			authUser.getId()
		);
		return generatedToken;
	}
}
