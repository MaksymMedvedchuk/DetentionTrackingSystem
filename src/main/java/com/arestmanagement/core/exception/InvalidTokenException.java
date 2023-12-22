package com.arestmanagement.core.exception;

public class InvalidTokenException extends RuntimeException{
	public InvalidTokenException(final String message) {
		super(message);
	}
}
