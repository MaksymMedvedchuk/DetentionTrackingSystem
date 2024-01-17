package com.detentionsystem.controller;

import com.detentionsystem.config.Constant;
import com.detentionsystem.core.converter.ConvertUserCreateData;
import com.detentionsystem.core.domain.dto.LoginDto;
import com.detentionsystem.core.domain.dto.UserDto;
import com.detentionsystem.core.domain.entity.User;
import com.detentionsystem.core.service.email.EmailService;
import com.detentionsystem.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

	private final static String URL = "localhost:8080";
	private final static String TOKEN_VALUE = "tokenValue";
	@InjectMocks
	private AuthController authController;
	@Mock
	private AuthenticationService authenticationService;
	@Mock
	private ConvertUserCreateData convertUserCreateData;
	@Mock
	private EmailService emailService;
	private User user;
	private UserDto userDto;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private LoginDto loginDto;

	@BeforeEach
	void setUp() {
		user = new User();
		userDto = new UserDto();
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		loginDto = new LoginDto();
	}

	@Test
	void testRegisterUser_SuccessfulRegistration() {
		final User currentUser = convertUserCreateData.convertToDatabaseColumn(userDto);

		when(authenticationService.register(currentUser)).thenReturn(user);
		when(emailService.getCurrentUrl(request)).thenReturn(URL);

		authController.registerUser(userDto, request);

		verify(authenticationService, times(1)).register(currentUser);
		verify(emailService, times(1)).sendVerificationEmail(user, URL);
	}

	@Test
	void testVerifyUser_SuccessfulVerification() {
		final ResponseEntity<String> responseEntity = authController.verifyUser(TOKEN_VALUE);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		verify(authenticationService).verify(TOKEN_VALUE);
	}

	@Test
	void testLoginUser_SuccessfulLogin() {
		when(authenticationService.login(loginDto)).thenReturn(TOKEN_VALUE);

		final ResponseEntity<String> responseEntity = authController.loginUser(loginDto, response);

		verify(authenticationService).login(loginDto);
		verify(response).setHeader(
			Constant.Token.AUTHORIZATION_HEADER,
			Constant.Token.ACCESS_TOKEN_PREFIX + TOKEN_VALUE
		);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(TOKEN_VALUE, responseEntity.getBody());

	}

	@Test
	void testLogoutUser_SuccessfulLogout() {
		when(request.getHeader(Constant.Token.AUTHORIZATION_HEADER)).thenReturn(TOKEN_VALUE);

		final ResponseEntity<String> responseEntity = authController.logoutUser(user.getId(), request);

		verify(authenticationService).logout(user.getId(), TOKEN_VALUE);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}



