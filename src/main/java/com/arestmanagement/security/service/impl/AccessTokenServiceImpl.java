package com.arestmanagement.security.service.impl;

import com.arestmanagement.core.domain.entity.Token;
import com.arestmanagement.core.domain.enums.TokenType;
import com.arestmanagement.core.service.DataTimeService;
import com.arestmanagement.security.model.AuthUser;
import com.arestmanagement.security.service.AccessTokenService;
import com.arestmanagement.security.tokengenerator.TokenExpirationTime;
import com.arestmanagement.security.tokengenerator.TokenGeneratorProvider;
import com.arestmanagement.security.tokengenerator.TokenSecretKey;
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
