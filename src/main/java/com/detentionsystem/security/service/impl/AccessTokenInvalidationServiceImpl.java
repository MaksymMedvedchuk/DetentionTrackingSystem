package com.detentionsystem.security.service.impl;

import com.detentionsystem.security.service.AccessTokenInvalidationService;
import com.detentionsystem.security.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class AccessTokenInvalidationServiceImpl implements AccessTokenInvalidationService {

	private final Set<String> tokens = new HashSet<>();

	public AccessTokenInvalidationServiceImpl(final TokenService tokenService) {
	}

	@Override
	public void invalidateAccessTokens(final String accessToken) {
		log.info("Invalidating access token status: [{}]", accessToken);
		tokens.add(accessToken);
	}

	@Override
	public boolean isAccessTokenInvalidated(final String accessToken) {
		log.info("Checking access token invalidation status: [{}]", accessToken);
		return tokens.contains(accessToken);
	}

	@Scheduled(cron = "0 0 0 * * ?")
	public void deleteInvalidatedTokens(){
		log.info("List of invalidation tokens: [{}]", tokens);
		tokens.clear();
	}
}
