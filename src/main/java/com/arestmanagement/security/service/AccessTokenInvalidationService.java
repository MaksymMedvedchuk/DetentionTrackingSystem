package com.arestmanagement.security.service;

import java.util.Collection;

public interface AccessTokenInvalidationService {

	void invalidateAccessTokens(String accessToken);

	boolean isAccessTokenInvalidated(String accessToken);
}
