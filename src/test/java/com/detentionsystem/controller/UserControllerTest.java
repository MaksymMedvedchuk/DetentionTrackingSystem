package com.detentionsystem.controller;

import com.detentionsystem.core.domain.entity.User;
import com.detentionsystem.core.exception.ResourceNotfoundException;
import com.detentionsystem.core.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;

	private User user;

	@BeforeEach
	void setUp() {
		user = new User();
	}

	@Test
	void shouldDeleteUser() {
		when(userService.getUserById(user.getId())).thenReturn(user);

		final ResponseEntity<String> response = userController.deleteUser(user.getId());

		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(userService).getUserById(user.getId());
		verify(userService).deleteUserById(user.getId());
	}
}
