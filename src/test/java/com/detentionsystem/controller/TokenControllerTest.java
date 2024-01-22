package com.detentionsystem.controller;

import com.detentionsystem.security.service.JwtTokenAuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TokenControllerTest {

	private static final String TOKEN_VALUE = "tokenValue";

	@InjectMocks
	private TokenController tokenController;

	@Mock
	private JwtTokenAuthenticationService jwtTokenAuthenticationService;

	@Test
	void shouldGenerateAccessTokenFromRefresh() {
		when(jwtTokenAuthenticationService.generateAccessTokenFromRefreshToken(TOKEN_VALUE)).thenReturn(TOKEN_VALUE);

		final ResponseEntity<String> response = tokenController.generateAccessTokenFromRefresh(TOKEN_VALUE);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void shouldGenerateNewRefreshToken() {
		when(jwtTokenAuthenticationService.generateNewRefreshToken(TOKEN_VALUE)).thenReturn(TOKEN_VALUE);

		final ResponseEntity<String> response = tokenController.generateNewRefreshToken(TOKEN_VALUE);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
