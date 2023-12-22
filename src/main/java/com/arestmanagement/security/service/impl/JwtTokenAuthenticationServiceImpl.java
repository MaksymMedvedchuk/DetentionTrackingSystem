package com.arestmanagement.security.service.impl;

import com.arestmanagement.core.domain.entity.Token;
import com.arestmanagement.core.domain.entity.User;
import com.arestmanagement.core.domain.enums.TokenType;
import com.arestmanagement.core.service.UserService;
import com.arestmanagement.security.model.AuthUser;
import com.arestmanagement.security.model.CustomUserServiceDetails;
import com.arestmanagement.security.service.AccessTokenInvalidationService;
import com.arestmanagement.security.service.AccessTokenService;
import com.arestmanagement.security.service.JwtTokenAuthenticationService;
import com.arestmanagement.security.service.RefreshTokenService;
import com.arestmanagement.security.service.TokenService;
import com.arestmanagement.security.tokengenerator.TokenSecretKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.security.Key;

@Slf4j
@Component
public class JwtTokenAuthenticationServiceImpl implements JwtTokenAuthenticationService {

	private final TokenSecretKey tokenSecretKey;

	private final TokenService tokenService;

	private final CustomUserServiceDetails customUserServiceDetails;

	private final UserService userService;

	private final AccessTokenService accessTokenService;

	private final RefreshTokenService refreshTokenService;

	private final HandlerExceptionResolver resolver;

	public JwtTokenAuthenticationServiceImpl(
		final TokenSecretKey tokenSecretKey,
		final TokenService tokenService,
		final CustomUserServiceDetails customUserServiceDetails,
		final UserService userService,
		final AccessTokenService accessTokenService,
		final RefreshTokenService refreshTokenService,
		@Qualifier("handlerExceptionResolver") final HandlerExceptionResolver resolver
	) {
		this.tokenSecretKey = tokenSecretKey;
		this.tokenService = tokenService;
		this.customUserServiceDetails = customUserServiceDetails;
		this.userService = userService;
		this.accessTokenService = accessTokenService;
		this.refreshTokenService = refreshTokenService;
		this.resolver = resolver;
	}

	@Override
	public boolean validateAccessToken(
		final String accessToken, final HttpServletRequest request,
		final HttpServletResponse response
	) {
		Key key = getSigningKey();
		try {
			Claims claims = getTokenPayload(accessToken, key);
			return true;
		} catch (Exception e) {
			handleJwtException(request, response, e);
		}
		return false;
	}

	private void handleJwtException(HttpServletRequest request, HttpServletResponse response, Exception e) {
		resolver.resolveException(request, response, null, e);

		if (e instanceof ExpiredJwtException) {
			log.warn("In handleJwtException - JWT token already expired");
		} else if (e instanceof UnsupportedJwtException) {
			log.error("In handleJwtException - Unsupported JWT token");
		} else if (e instanceof MalformedJwtException) {
			log.error("In handleJwtException - Invalid JWT token");
		} else if (e instanceof SignatureException) {
			log.error("In handleJwtException - Invalid JWT signature");
		} else if (e instanceof IllegalArgumentException) {
			log.error("In handleJwtException - JWT claims string is empty");
		} else {
			log.error("In validateJwtToken - Unexpected JWT exception", e);
		}
	}

	private Key getSigningKey() {
		Key key = tokenSecretKey.getTokenKey(TokenType.ACCESS);
		return key;
	}

	private Claims getTokenPayload(final String jwtToken, final Key key) {
		return Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken).getBody();
	}

	private String getSubjectFromClaims(final Claims claims) {
		return claims.getSubject();
	}

	@Override
	public String getSubjectFromToken(final String accessToken) {
		Key key = getSigningKey();
		String subject = getTokenPayload(accessToken, key).getSubject();
		return subject;
	}

	@Override
	public String generateAccessTokenFromRefreshToken(final String refreshTokenValue) {
		final Token token = tokenService.findToken(refreshTokenValue, TokenType.REFRESH);
		final User user = userService.getUserById(token.getUserId());
		if (tokenService.validateToken(token)) {

			final AuthUser authUser = customUserServiceDetails.loadUserByUsername(user.getEmail());

			log.debug(
				"In generateAccessTokenFromRefreshToken generated new access token for user with email: [{}]",
				authUser.getEmail()
			);
			return accessTokenService.generateAccessToken(authUser).getTokenValue();
		}
		log.info("In generateAccessTokenFromRefreshToken failed to generate new access token: "
			+ "refresh token is invalid");
		return "";
	}

	@Override
	public String generateNewRefreshToken(final String oldRefreshToken) {
		Long userId = tokenService.findUserId(oldRefreshToken, TokenType.REFRESH);
		tokenService.deleteAllTokensByUserId(userId);
		log.debug("In generateNewRefreshToken deleted all tokens be userId: [{}]", userId);

		final AuthUser authUser = getAuthUser(userId);
		Token refreshToken = refreshTokenService.generateRefreshToken(authUser);

		log.debug(
			"In generateNewRefreshToken generated new refresh token for user by email: [{}]",
			authUser.getEmail()
		);
		return refreshToken.getTokenValue();
	}

	private AuthUser getAuthUser(final Long userId) {
		User user = userService.getUserById(userId);
		final AuthUser authUser = customUserServiceDetails.loadUserByUsername(user.getEmail());
		return authUser;
	}
}
