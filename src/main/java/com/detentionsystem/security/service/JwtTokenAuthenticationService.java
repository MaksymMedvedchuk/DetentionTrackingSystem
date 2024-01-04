package com.detentionsystem.security.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface JwtTokenAuthenticationService {

	boolean validateAccessToken(String accessToken, HttpServletRequest request,
		 HttpServletResponse response);

	public String getSubjectFromToken(String accessToken);

	String generateAccessTokenFromRefreshToken(String refreshTokenValue);

	String generateNewRefreshToken(String oldRefreshToken);
}
