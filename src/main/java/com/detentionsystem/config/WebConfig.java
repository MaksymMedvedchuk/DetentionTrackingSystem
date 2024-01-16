package com.detentionsystem.config;

import com.detentionsystem.security.filter.JwtAuthorizationFilter;
import com.detentionsystem.security.model.CustomUserServiceDetails;
import com.detentionsystem.security.service.AccessTokenInvalidationService;
import com.detentionsystem.security.service.JwtTokenAuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebConfig {

	private static final String[]
		SWAGGER_PATHS =
		{"/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**", "/webjars/swagger-ui/**"};

	private final CustomUserServiceDetails customUserServiceDetails;

	private final JwtTokenAuthenticationService jwtTokenAuthenticationService;

	private final AccessTokenInvalidationService accessTokenInvalidationService;

	public WebConfig(
		final CustomUserServiceDetails customUserServiceDetails,
		final JwtTokenAuthenticationService jwtTokenAuthenticationService,
		final AccessTokenInvalidationService accessTokenInvalidationService
	) {
		this.customUserServiceDetails = customUserServiceDetails;
		this.jwtTokenAuthenticationService = jwtTokenAuthenticationService;
		this.accessTokenInvalidationService = accessTokenInvalidationService;
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable()
			.authorizeRequests(authorizeRequests ->
				authorizeRequests
					.requestMatchers(SWAGGER_PATHS).permitAll()
					.requestMatchers(HttpMethod.DELETE, "/v1.0/user/delete/*").hasRole("PERSONE")
					.requestMatchers(HttpMethod.POST, "/detention/create").hasRole("ADMIN")
					.requestMatchers(HttpMethod.GET, "/detention/get/*").hasAnyRole("ADMIN", "PERSONE")
					.requestMatchers(HttpMethod.POST,
						"/v1.0/auth/registration",
						"/v1.0/auth/login",
						"/v1.0/auth/verify_user",
						"/v1.0/token/generate-access-token",
						"/v1.0/token/generate-refresh-token",
						"/v1.0/email"
						).permitAll()
					.anyRequest().authenticated()
			)
			.sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
			.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
			.build();
	}

	@Bean
	public JwtAuthorizationFilter jwtAuthorizationFilter() {
		return new JwtAuthorizationFilter(customUserServiceDetails, jwtTokenAuthenticationService,
			accessTokenInvalidationService
		);
	}
}

