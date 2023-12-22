package com.arestmanagement.security.service.impl;

import com.arestmanagement.core.domain.dto.LoginDto;
import com.arestmanagement.core.domain.entity.Token;
import com.arestmanagement.core.domain.entity.User;
import com.arestmanagement.core.domain.enums.TokenType;
import com.arestmanagement.core.service.DataTimeService;
import com.arestmanagement.core.service.UserService;
import com.arestmanagement.security.model.AuthUser;
import com.arestmanagement.security.service.AccessTokenService;
import com.arestmanagement.security.service.TokenService;
import com.arestmanagement.security.tokengenerator.TokenExpirationTime;
import com.arestmanagement.security.tokengenerator.TokenGeneratorProvider;
import com.arestmanagement.security.tokengenerator.TokenSecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccessTokenServiceImpl extends TokenGeneratorProvider implements AccessTokenService {

	private final TokenService tokenService;

	private final UserService userService;

	public AccessTokenServiceImpl(
		final TokenService tokenService,
		final TokenExpirationTime tokenExpirationTime,
		final DataTimeService dataTimeService,
		final TokenSecretKey tokenSecretKey,

		final UserService userService
	) {
		super(tokenExpirationTime, dataTimeService, tokenSecretKey);
		this.tokenService = tokenService;
		this.userService = userService;
	}

	@Override
	public Token generateAccessToken(final AuthUser authUser) {
		final Token generatedToken = generateToken(authUser, TokenType.ACCESS);
		//tokenService.saveToken(generatedToken);
		log.debug(
			"In generateAccessToken: successfully create VerificationToke for user with id: {}",
			authUser.getId()
		);
		return generatedToken;
	}
}
