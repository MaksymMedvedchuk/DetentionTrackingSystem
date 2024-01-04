package com.detentionsystem.security.service.impl;

import com.detentionsystem.core.domain.entity.Token;
import com.detentionsystem.core.domain.enums.TokenType;
import com.detentionsystem.core.service.DataTimeService;
import com.detentionsystem.security.model.AuthUser;
import com.detentionsystem.security.service.RefreshTokenService;
import com.detentionsystem.security.service.TokenService;
import com.detentionsystem.security.tokengenerator.TokenExpirationTime;
import com.detentionsystem.security.tokengenerator.TokenGeneratorProvider;
import com.detentionsystem.security.tokengenerator.TokenSecretKey;
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
