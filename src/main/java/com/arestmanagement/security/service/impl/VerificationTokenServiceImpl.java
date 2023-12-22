package com.arestmanagement.security.service.impl;

import com.arestmanagement.config.Constant;
import com.arestmanagement.core.domain.entity.Token;
import com.arestmanagement.core.domain.entity.User;
import com.arestmanagement.core.domain.enums.TokenType;
import com.arestmanagement.core.service.DataTimeService;
import com.arestmanagement.security.tokengenerator.TokenExpirationTime;
import com.arestmanagement.security.tokengenerator.TokenSecretKey;
import com.arestmanagement.security.service.TokenService;
import com.arestmanagement.security.service.VerificationTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

	private final TokenService tokenService;

	private final TokenExpirationTime tokenExpirationTime;

	private final DataTimeService dataTimeService;

	private final TokenSecretKey tokenSecretKey;

	public VerificationTokenServiceImpl(
		final TokenService tokenService,
		final TokenExpirationTime tokenExpirationTime,
		final DataTimeService dataTimeService,
		final TokenSecretKey tokenSecretKey
	) {
		this.tokenService = tokenService;
		this.tokenExpirationTime = tokenExpirationTime;
		this.dataTimeService = dataTimeService;
		this.tokenSecretKey = tokenSecretKey;
	}

	@Override
	public Token generateVerificationToken(final User user) {
		final Token generatedToken = generateToken(user, TokenType.VERIFICATION);
		tokenService.saveToken(generatedToken);
		log.debug(
			"In generateVerificationToken: successfully create VerificationToke for user with id: {}",
			user.getId()
		);
		return generatedToken;
	}

	private Token generateToken(final User user, final TokenType tokenType) {
		final long actTime = tokenExpirationTime.getActTimeToken(tokenType);
		final LocalDateTime expirationTime = dataTimeService.now().plus(actTime, ChronoUnit.MILLIS);
		final String jwt = createJwtValue(user, expirationTime, tokenType);

		log.info("In generateToken: create new Token for user with id {} and TokenType {}", user.getId(), tokenType);

		return Token.builder()
			.userId(user.getId())
			.tokenValue(jwt)
			.tokenType(tokenType)
			.expirationDate(expirationTime)
			.build();
	}

	private String createJwtValue(
		final User user,
		final LocalDateTime expirationDate,
		final TokenType tokenType
	) {
		final Claims claims = Jwts.claims().setSubject(user.getEmail());
		claims.put(Constant.Claim.ID, user.getId());
		claims.put(Constant.Claim.EMAIL, user.getEmail());
		claims.put(Constant.Claim.ROLE, user.getRole());

		final Key key = getKey(tokenType);

		log.info("In createJwtValue trying to create jwt for user with email {}", user.getEmail());
		return Jwts.builder()
			.setIssuedAt(dataTimeService.toDate(LocalDateTime.now()))
			.setClaims(claims)
			.setExpiration(dataTimeService.toDate(expirationDate))
			.signWith(key)
			.compact();
	}

	private Key getKey(final TokenType tokenType) {
		log.info("In getKey trying to get key by tokenType {}", tokenType);
		return tokenSecretKey.getTokenKey(tokenType);
	}
}
