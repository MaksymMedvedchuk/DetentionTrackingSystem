package com.arestmanagement.security.service;

import com.arestmanagement.core.domain.dto.LoginDto;
import com.arestmanagement.core.domain.entity.User;

public interface AuthenticationService {

	User register(User user);

	void verify(String tokenValue);

	String login(LoginDto loginDto);

	void logout(Long userId, String accessToken);
}
