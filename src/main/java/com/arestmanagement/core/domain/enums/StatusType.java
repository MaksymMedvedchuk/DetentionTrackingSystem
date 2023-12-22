package com.arestmanagement.core.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
@AllArgsConstructor
public enum StatusType {

    ACTIVE(1),
    COMPLETED(2),
    CANCELED(3);

    private final Integer code;

    public static StatusType getByCode(Integer code) {
        for (StatusType element : StatusType.values()) {
            if (element.getCode().equals(code)) return element;
        }
        throw new NoSuchElementException("No element for code " + code);
    }
}
