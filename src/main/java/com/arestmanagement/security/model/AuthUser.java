package com.arestmanagement.security.model;

import com.arestmanagement.core.domain.enums.Role;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
public class AuthUser implements UserDetails {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Role role;
	private boolean isVerified;
	private final Collection<? extends GrantedAuthority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	/*private Collection<? extends GrantedAuthority> toAuthority(final Role role) {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}*/
}
