package com.arestmanagement.util;

public enum OptionType {

    PRIMARY("primary"),
    CHANGED("changed"),
    CANCELED("canceled");

    public String operation;

    OptionType(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}
