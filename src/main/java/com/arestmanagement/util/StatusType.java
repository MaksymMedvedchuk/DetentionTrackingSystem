package com.arestmanagement.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
@AllArgsConstructor
public enum StatusType {

    ACTIVE(1, "ACTIVE"),
    COMPLETED(2, "COMPLETED"),
    CANCELED(3, "CANCELED");

    private final Integer code;
    private final String type;

    public static StatusType getByCode(Integer code) {
        for (StatusType element : StatusType.values()) {
            if (element.getCode().equals(code)) return element;
        }
        throw new NoSuchElementException("No element for code " + code);
    }
}
