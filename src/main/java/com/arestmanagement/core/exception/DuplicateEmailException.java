package com.arestmanagement.core.exception;

public class DuplicateEmailException extends RuntimeException{

	public DuplicateEmailException(String message) {
		super(message);
	}
}
