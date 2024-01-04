package com.detentionsystem.security.service;

import com.detentionsystem.core.domain.entity.Token;
import com.detentionsystem.security.model.AuthUser;

public interface RefreshTokenService {

	Token generateRefreshToken(AuthUser authUser);
}
