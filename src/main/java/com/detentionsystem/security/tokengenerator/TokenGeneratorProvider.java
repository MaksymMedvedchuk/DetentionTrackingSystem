package com.detentionsystem.security.tokengenerator;

import com.detentionsystem.config.Constant;
import com.detentionsystem.core.domain.entity.Token;
import com.detentionsystem.core.domain.enums.TokenType;
import com.detentionsystem.core.service.DataTimeService;
import com.detentionsystem.security.model.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
public abstract class TokenGeneratorProvider {

	private final TokenExpirationTime tokenExpirationTime;

	private final DataTimeService dataTimeService;

	private final TokenSecretKey tokenSecretKey;

	protected TokenGeneratorProvider(
		final TokenExpirationTime tokenExpirationTime,
		final DataTimeService dataTimeService,
		final TokenSecretKey tokenSecretKey
	) {
		this.tokenExpirationTime = tokenExpirationTime;
		this.dataTimeService = dataTimeService;
		this.tokenSecretKey = tokenSecretKey;
	}

	protected Token generateToken(final AuthUser authUser, final TokenType tokenType) {
		final long actTime = tokenExpirationTime.getActTimeToken(tokenType);
		final LocalDateTime expirationTime = dataTimeService.now().plus(actTime, ChronoUnit.MILLIS);
		final String jwt = createJwtValue(authUser, expirationTime, tokenType);

		log.info("In generateToken: create new Token for user with id {} and TokenType {}", authUser.getId(), tokenType);

		return Token.builder()
			.userId(authUser.getId())
			.tokenValue(jwt)
			.tokenType(tokenType)
			.expirationDate(expirationTime)
			.build();
	}

	private String createJwtValue(
		final AuthUser authUser,
		final LocalDateTime expirationDate,
		final TokenType tokenType
	) {
		final Claims claims = Jwts.claims().setSubject(authUser.getEmail());
		claims.put(Constant.Claim.ID, authUser.getId());
		claims.put(Constant.Claim.EMAIL, authUser.getEmail());
		claims.put(Constant.Claim.ROLE, authUser.getRole());

		final Key key = getKey(tokenType);

		log.info("In createJwtValue trying to create jwt for user with email {}", authUser.getEmail());
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
