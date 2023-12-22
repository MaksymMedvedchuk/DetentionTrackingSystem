package com.arestmanagement.security.service;

import com.arestmanagement.core.domain.dto.LoginDto;
import com.arestmanagement.core.domain.entity.Token;
import com.arestmanagement.security.model.AuthUser;

public interface AccessTokenService {

	Token generateAccessToken(AuthUser authUser);

	/*String getValueFromAccessToken(LoginDto loginDto);*/
}
