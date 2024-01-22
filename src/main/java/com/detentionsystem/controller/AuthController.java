package com.detentionsystem.controller;

import com.detentionsystem.config.Constant;
import com.detentionsystem.core.converter.ConvertUserData;
import com.detentionsystem.core.domain.dto.LoginDto;
import com.detentionsystem.core.domain.dto.UserDto;
import com.detentionsystem.core.domain.entity.User;
import com.detentionsystem.core.service.email.EmailService;
import com.detentionsystem.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.detentionsystem.controller.AuthController.URL;

@Slf4j
@RestController
@RequestMapping(Constant.VersionApi.VERSION + URL)
public class AuthController {

	protected static final String URL = "/auth";

	private final AuthenticationService authenticationService;

	private final ConvertUserData convertUserData;

	private final EmailService emailService;

	public AuthController(
		final AuthenticationService authenticationService,
		final ConvertUserData convertUserData,
		final EmailService emailService
	) {
		this.authenticationService = authenticationService;
		this.convertUserData = convertUserData;
		this.emailService = emailService;
	}

	@PostMapping("/registration")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Registration new user")
	public void registerUser(@RequestBody @Valid final UserDto userDto, final HttpServletRequest request) {
		User currentUser = convertUserData.convertToDatabaseColumn(userDto);
		User savedUser = authenticationService.register(currentUser);
		log.debug("In registerUser: created a user with id: [{}]", savedUser.getId());

		String url = emailService.getCurrentUrl(request);
		emailService.sendVerificationEmail(savedUser, url);
		log.debug("Send verification code to email: [{}]", savedUser.getEmail());
	}

	@PostMapping("/verify_user")
	@Operation(summary = "Confirm user email")
	public ResponseEntity<String> verifyUser(@RequestParam final String code) {
		authenticationService.verify(code);

		log.debug("In verifyUser trying verify user with code: [{}]", code);
		return ResponseEntity.ok("verified");
	}

	@PostMapping("/login")
	@Operation(summary = "Login for user")
	public ResponseEntity<String> loginUser(
		@RequestBody @Valid final LoginDto loginDto,
		final HttpServletResponse response
	) {
		String accessJwt = authenticationService.login(loginDto);
		response.setHeader("Authorization", "Bearer " + accessJwt);

		log.info("In loginUser successful login for user with email: [{}]", loginDto.getEmail());
		return new ResponseEntity<>(accessJwt, HttpStatus.OK);
	}

	@PostMapping("/logout/{userId}")
	@Operation(summary = "Logout user")
	@PreAuthorize("hasRole('ADMIN') or hasRole('PERSONE')")
	@SecurityRequirement(name = "Authorization")
	public ResponseEntity<String> logoutUser(@PathVariable final Long userId, final HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization").replace("Bearer ", "");
		authenticationService.logout(userId, accessToken);

		log.info("In logoutUser successful logout for user with id: [{}]", userId);
		return ResponseEntity.ok("logout successful");
	}
}
