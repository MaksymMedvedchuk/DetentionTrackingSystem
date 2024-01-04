package com.detentionsystem.core.exception;

public class InvalidTokenException extends RuntimeException{
	public InvalidTokenException(final String message) {
		super(message);
	}
}
