package com.arestmanagement.controller;

import com.arestmanagement.config.Constant;
import com.arestmanagement.core.converter.ConvertTokenData;
import com.arestmanagement.security.service.JwtTokenAuthenticationService;
import com.arestmanagement.security.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.arestmanagement.controller.TokenController.URL;

@Slf4j
@RestController
@RequestMapping(Constant.VersionApi.VERSION + URL)
public class TokenController {

	protected static final String URL = "/token";

	private final ConvertTokenData convertTokenData;

	private final TokenService tokenService;

	private final JwtTokenAuthenticationService jwtTokenAuthenticationService;

	public TokenController(
		final ConvertTokenData convertTokenData, final TokenService tokenService,
		final JwtTokenAuthenticationService jwtTokenAuthenticationService
	) {
		this.convertTokenData = convertTokenData;
		this.tokenService = tokenService;
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
