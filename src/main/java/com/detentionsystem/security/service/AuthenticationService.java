package com.detentionsystem.security.service;

import com.detentionsystem.core.domain.dto.LoginDto;
import com.detentionsystem.core.domain.entity.User;

public interface AuthenticationService {

	User register(User user);

	void verify(String tokenValue);

	String login(LoginDto loginDto);

	void logout(Long userId, String accessToken);
}
