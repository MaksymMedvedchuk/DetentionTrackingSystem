package com.arestmanagement.exception;

public class ArrestNotFoundException extends RuntimeException {

    public ArrestNotFoundException(String message) {
        super(message);
    }

    /* private final String massage;

    public ArrestNotFoundException(String massage) {
        this.massage = massage;
    }

    public String getMassage() {
        return massage;
    }*/
}
