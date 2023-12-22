package com.arestmanagement.security.service.impl;

import com.arestmanagement.core.domain.dto.LoginDto;
import com.arestmanagement.core.domain.entity.Token;
import com.arestmanagement.core.domain.entity.User;
import com.arestmanagement.core.domain.enums.Role;
import com.arestmanagement.core.domain.enums.TokenType;
import com.arestmanagement.core.exception.DuplicateEmailException;
import com.arestmanagement.core.repository.EmailRepository;
import com.arestmanagement.core.repository.UserRepository;
import com.arestmanagement.core.service.UserService;
import com.arestmanagement.security.model.AuthUser;
import com.arestmanagement.security.model.CustomUserServiceDetails;
import com.arestmanagement.security.service.AccessTokenInvalidationService;
import com.arestmanagement.security.service.AccessTokenService;
import com.arestmanagement.security.service.AuthenticationService;
import com.arestmanagement.security.service.RefreshTokenService;
import com.arestmanagement.security.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final EmailRepository emailRepository;

	private final TokenService tokenService;

	private final UserService userService;

	private final AccessTokenService accessTokenService;

	private final RefreshTokenService refreshTokenService;

	private final CustomUserServiceDetails customUserServiceDetails;

	private final AccessTokenInvalidationService accessTokenInvalidationService;

	public AuthenticationServiceImpl(
		final UserRepository userRepository,
		final BCryptPasswordEncoder bCryptPasswordEncoder,
		final EmailRepository emailRepository,
		final TokenService tokenService,
		final UserService userService,
		final AccessTokenService accessTokenService,
		final RefreshTokenService refreshTokenService,
		final CustomUserServiceDetails customUserServiceDetails,
		final AccessTokenInvalidationService accessTokenInvalidationService
	) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.emailRepository = emailRepository;
		this.tokenService = tokenService;
		this.userService = userService;
		this.accessTokenService = accessTokenService;
		this.refreshTokenService = refreshTokenService;
		this.customUserServiceDetails = customUserServiceDetails;
		this.accessTokenInvalidationService = accessTokenInvalidationService;
	}

	@Override
	public User register(final User user) {
		String email = user.getEmail();
		Role role = user.getRole();
		if (userRepository.findAllByEmail(email).isPresent()) {
			throw new DuplicateEmailException("User with email already exists: " + email);
		}
		if (user.getPassword() != null) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		}
		if (emailRepository.findAllByEmail(email).isPresent()) {
			user.setRole(Role.ADMIN);
		} else {
			user.setRole(Role.PERSONE);
		}
		user.setVerified(false);
		log.info("In register created user with email: [{}]", user.getEmail());
		return userRepository.save(user);
	}

	@Override
	public void verify(final String tokenValue) {
		final Token token = tokenService.findToken(tokenValue, TokenType.VERIFICATION);

		tokenService.validateToken(token);

		Long userId = tokenService.findUserId(tokenValue, TokenType.VERIFICATION);
		userService.updateIsVariried(userId);
		tokenService.deleteAllTokensByUserId(userId);
	}

	@Override
	public String login(final LoginDto loginDto) {
		final User user = userService.findUserByEmail(loginDto.getEmail());

		final boolean wrongPassword = !bCryptPasswordEncoder.matches(loginDto.getPassword(), user.getPassword());
		final boolean unvarifiedEmail = !user.isVerified();

		if (wrongPassword || unvarifiedEmail) {
			if (wrongPassword) {
				log.warn("In authenticate - wrong password attempt for email: [{}]", loginDto.getPassword());
			}
			if (unvarifiedEmail) {
				log.warn("In authenticate - user dowsn't varified fir email: [{}]", loginDto.getEmail());
			}
			throw new BadCredentialsException("Invalid login/password or unvarified user");
		}

		UserDetails authUser = getUserDetails(user);
		addRefreshTokenToDb((AuthUser) authUser);

		log.info("In login trying to generaet access token value for email: [{}]", ((AuthUser) authUser).getEmail());
		return accessTokenService.generateAccessToken((AuthUser) authUser).getTokenValue();
	}

	@Override
	public void logout(final Long userId, String accessToken) {
		User user = userService.getUserById(userId);
		tokenService.deleteAllTokensByUserId(userId);
		accessTokenInvalidationService.invalidateAccessTokens(accessToken);
		log.info("In logout tryin to logout user with email: [{}]", user.getEmail());
	}

	private UserDetails getUserDetails(final User user) {
		UserDetails authUser = customUserServiceDetails.loadUserByUsername(user.getEmail());
		Authentication
			authentication =
			new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return authUser;
	}

	private void addRefreshTokenToDb(final AuthUser authUser) {
		Token refreshToken = refreshTokenService.generateRefreshToken(authUser);
		tokenService.saveToken(refreshToken);
	}
}
