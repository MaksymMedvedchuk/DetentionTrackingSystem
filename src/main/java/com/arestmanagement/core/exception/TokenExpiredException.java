package com.arestmanagement.core.exception;

public class TokenExpiredException extends ValidationException {
	public TokenExpiredException(final String message) {
		super(message);
	}
}
