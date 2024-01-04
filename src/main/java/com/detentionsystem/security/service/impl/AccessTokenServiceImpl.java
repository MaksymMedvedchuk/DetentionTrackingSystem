package com.detentionsystem.security.service.impl;

import com.detentionsystem.core.domain.entity.Token;
import com.detentionsystem.core.domain.enums.TokenType;
import com.detentionsystem.core.service.DataTimeService;
import com.detentionsystem.security.model.AuthUser;
import com.detentionsystem.security.service.AccessTokenService;
import com.detentionsystem.security.tokengenerator.TokenExpirationTime;
import com.detentionsystem.security.tokengenerator.TokenGeneratorProvider;
import com.detentionsystem.security.tokengenerator.TokenSecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccessTokenServiceImpl extends TokenGeneratorProvider implements AccessTokenService {

	public AccessTokenServiceImpl(
		final TokenExpirationTime tokenExpirationTime,
		final DataTimeService dataTimeService,
		final TokenSecretKey tokenSecretKey
	) {
		super(tokenExpirationTime, dataTimeService, tokenSecretKey);
	}

	@Override
	public Token generateAccessToken(final AuthUser authUser) {
		final Token generatedToken = generateToken(authUser, TokenType.ACCESS);
		log.debug("In generateAccessToken successfully create access token for user with id: {}", authUser.getId());
		return generatedToken;
	}
}
