package com.arestmanagement.security.service;

import com.arestmanagement.core.domain.dto.TokenDto;
import com.arestmanagement.core.domain.dto.UserDto;
import com.arestmanagement.core.domain.entity.Token;
import com.arestmanagement.core.domain.entity.User;

public interface VerificationTokenService {

	Token generateVerificationToken(User user);

}
