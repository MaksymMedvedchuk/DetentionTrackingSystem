package com.detentionsystem.controller;

import com.detentionsystem.config.Constant;
import com.detentionsystem.security.service.JwtTokenAuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.detentionsystem.controller.TokenController.URL;

@Slf4j
@RestController
@RequestMapping(Constant.VersionApi.VERSION + URL)
public class TokenController {

	protected static final String URL = "/token";

	private final JwtTokenAuthenticationService jwtTokenAuthenticationService;

	public TokenController(
		final JwtTokenAuthenticationService jwtTokenAuthenticationService
	) {
		this.jwtTokenAuthenticationService = jwtTokenAuthenticationService;
	}

	@PostMapping("/generate-access-token")
	@Operation(summary = "Generate access token using refresh token")
	public ResponseEntity<String> generateAccessTokenFromRefresh(@RequestParam final String refreshToken) {
		log.debug("Received POST request to generate new access token from refresh token");
		String newAccessTokenValue = jwtTokenAuthenticationService.generateAccessTokenFromRefreshToken(refreshToken);
		return new ResponseEntity<>(newAccessTokenValue, HttpStatus.OK);
	}

	@PostMapping("/generate-refresh-token")
	@Operation(summary = "Generate new refresh token")
	public ResponseEntity<String> generateNewRefreshToken(@RequestParam String oldRefreshToken) {
		log.debug("Received POST request to generate new refresh token");
		String newRefreshToken = jwtTokenAuthenticationService.generateNewRefreshToken(oldRefreshToken);
		return new ResponseEntity<>(newRefreshToken, HttpStatus.OK);
	}
}
