package com.detentionsystem.security.service;

import com.detentionsystem.core.domain.entity.Token;
import com.detentionsystem.security.model.AuthUser;

public interface AccessTokenService {

	Token generateAccessToken(AuthUser authUser);

	/*String getValueFromAccessToken(LoginDto loginDto);*/
}
