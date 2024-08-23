package com.detentionsystem.controller;

import com.detentionsystem.config.Constant;
import com.detentionsystem.core.domain.entity.User;
import com.detentionsystem.core.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.detentionsystem.controller.UserController.URL;

@Slf4j
@RestController
@RequestMapping(Constant.VersionApi.VERSION + URL)
public class UserController {

	protected static final String URL = "/user";

	private final UserService userService;

	public UserController(final UserService userService) {
		this.userService = userService;
	}

	@DeleteMapping("/delete/{userId}")
	@Operation(summary = "Delete user and tokens by user id")
	@PreAuthorize("hasRole('ADMIN') or hasRole('PERSONE')")
	@SecurityRequirement(name = "Authorization")
	public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
		User user = userService.getUserById(userId);
		userService.deleteUserById(user.getId());
		log.debug("In deleteUser: deleted user with id: {}", userId);
		return new ResponseEntity<>("delete successful", HttpStatus.OK);
	}
}
