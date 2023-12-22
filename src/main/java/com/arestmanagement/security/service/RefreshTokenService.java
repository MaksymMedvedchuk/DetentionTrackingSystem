package com.arestmanagement.security.service;

import com.arestmanagement.core.domain.entity.Token;
import com.arestmanagement.core.domain.entity.User;
import com.arestmanagement.security.model.AuthUser;

public interface RefreshTokenService {

	Token generateRefreshToken(AuthUser authUser);
}
