package com.arestmanagement.security.model;

import com.arestmanagement.core.domain.entity.User;
import com.arestmanagement.core.domain.enums.Role;
import com.arestmanagement.core.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserServiceDetails implements UserDetailsService {

	private final UserService userService;

	public CustomUserServiceDetails(final UserService userService) {
		this.userService = userService;
	}

	@Override
	public AuthUser loadUserByUsername(final String email) throws UsernameNotFoundException {
		User user = userService.findUserByEmail(email);

		return AuthUser.builder()
			.id(user.getId())
			.firstName(user.getFirstName())
			.lastName(user.getLastName())
			.email(user.getEmail())
			.password(user.getPassword())
			.role(user.getRole())
			.isVerified(user.isVerified())
			.authorities(setAuthorities(user.getRole()))
			.build();
	}

	private Collection<? extends GrantedAuthority> setAuthorities(final Role role) {
		final Set<GrantedAuthority> authoritySet = new HashSet<>();
		authoritySet.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
		return authoritySet;
	}
}
