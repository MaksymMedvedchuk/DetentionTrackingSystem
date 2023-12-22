package com.arestmanagement.security.filter;

import com.arestmanagement.config.Constant;
import com.arestmanagement.security.model.AuthUser;
import com.arestmanagement.security.model.CustomUserServiceDetails;
import com.arestmanagement.security.service.AccessTokenInvalidationService;
import com.arestmanagement.security.service.JwtTokenAuthenticationService;
import com.arestmanagement.security.tokengenerator.TokenSecretKey;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final CustomUserServiceDetails customUserServiceDetails;

	private final JwtTokenAuthenticationService jwtTokenAuthenticationService;

	private final AccessTokenInvalidationService accessTokenInvalidationService;

	public JwtAuthorizationFilter(
		final CustomUserServiceDetails customUserServiceDetails,
		final JwtTokenAuthenticationService jwtTokenAuthenticationService,
		final AccessTokenInvalidationService accessTokenInvalidationService
	) {
		this.customUserServiceDetails = customUserServiceDetails;
		this.jwtTokenAuthenticationService = jwtTokenAuthenticationService;
		this.accessTokenInvalidationService = accessTokenInvalidationService;
	}

	@Override
	protected void doFilterInternal(
		final HttpServletRequest request,
		final HttpServletResponse response,
		final FilterChain filterChain
	)
		throws ServletException, IOException {

		final String accessToken = getAccessTokenFromRequest(request);
		if (isAccessToken(request)) {
			if (!accessToken.isEmpty() &&
				jwtTokenAuthenticationService.validateAccessToken(accessToken, request, response)) {
				String subject = jwtTokenAuthenticationService.getSubjectFromToken(accessToken);
				final AuthUser authUser = customUserServiceDetails.loadUserByUsername(subject);
				if (!accessTokenInvalidationService.isAccessTokenInvalidated(accessToken)) {
					final Authentication
						authentication =
						new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authentication);

					log.info(
						"In doFilterInternal successfully authenticated user with email: [{}] using access token",
						authUser.getUsername()
					);
				} else {
					SecurityContextHolder.clearContext();
				}
			} else {
				SecurityContextHolder.clearContext();
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

	private String getAccessTokenFromRequest(final HttpServletRequest request) {
		if (isAccessToken(request)) {
			String jwtAccessToken = request.getHeader(Constant.Token.AUTHORIZATION_HEADER)
				.replace(Constant.Token.ACCESS_TOKEN_PREFIX, "");
			return jwtAccessToken;
		}
		return "";
	}

	private boolean isAccessToken(HttpServletRequest request) {
		String authenticationHeader = request.getHeader(Constant.Token.AUTHORIZATION_HEADER);
		return StringUtils.startsWithIgnoreCase(authenticationHeader, Constant.Token.ACCESS_TOKEN_PREFIX);
	}
}



