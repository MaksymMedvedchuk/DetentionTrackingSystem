package com.detentionsystem.security.service;

public interface AccessTokenInvalidationService {

	void invalidateAccessTokens(String accessToken);

	boolean isAccessTokenInvalidated(String accessToken);
}
