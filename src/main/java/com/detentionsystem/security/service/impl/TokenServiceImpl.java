package com.detentionsystem.security.service.impl;

import com.detentionsystem.core.domain.entity.Token;
import com.detentionsystem.core.domain.enums.TokenType;
import com.detentionsystem.core.exception.ResourceNotfoundException;
import com.detentionsystem.core.exception.TokenExpiredException;
import com.detentionsystem.core.repository.TokenRepository;
import com.detentionsystem.core.service.DataTimeService;
import com.detentionsystem.security.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static java.lang.String.format;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

	private final TokenRepository tokenRepository;

	private final DataTimeService dataTimeService;

	public TokenServiceImpl(

		final TokenRepository tokenRepository,
		final DataTimeService dataTimeService
	) {
		this.tokenRepository = tokenRepository;
		this.dataTimeService = dataTimeService;
	}

	@Override
	public Token findToken(final String tokenValue, final TokenType tokenType) {
		log.debug("In findToken - Searching for token of type:[{}] with value:[{}]", tokenType, tokenValue);
		return tokenRepository.findByTokenValueAndTokenType(tokenValue, tokenType)
			.orElseThrow(() -> new ResourceNotfoundException(
				format("No token of type:%s with value:%s was found", tokenType, tokenValue)));
	}

	@Override
	public Long findUserId(final String tokenValue, final TokenType tokenType) {
		log.info("In findUserId trying to find userId by tokenValue and tokenType: [{}][{}]", tokenValue, tokenType);
		Long userId = tokenRepository.findByUserId(tokenType, tokenValue)
			.orElseThrow(() -> new ResourceNotfoundException("UserId not found"));
		return userId;
	}

	@Override
	@Transactional
	public void deleteAllTokensByUserId(final Long userId) {
		log.info("In deleteAllTokensByUserId trying delete tokens with userid: [{}]", userId);
		tokenRepository.deleteAllByUserId(userId);
	}

	@Override
	@Transactional
	public Token saveToken(final Token token) {
		log.debug(
			"In saveToken trying save token");
		return tokenRepository.saveAndFlush(token);
	}

	@Override
	public String findTokenValue(final Long userId, final TokenType tokenType) {
		log.info("In findTokenValue trying find tokenValue with userid: [{}]", userId);
		String jwt = tokenRepository.findTokenValue(userId, tokenType)
			.orElseThrow(() -> new ResourceNotfoundException("TokenValue bot found"));
		return jwt;
	}

	@Override
	public boolean validateToken(final Token token) {
		final LocalDateTime now = dataTimeService.now();
		final boolean valid = token.getExpirationDate().isAfter(now);
		if (valid) {
			log.info(
				"In validateToken trying validate token with type [{}] with id [{}]",
				token.getTokenType(),
				token.getId()
			);
			return true;
		} else {
			throw new TokenExpiredException("Refresh JWT token already expired");
		}
	}
}

