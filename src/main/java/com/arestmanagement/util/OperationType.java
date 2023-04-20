package com.arestmanagement.util;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
@AllArgsConstructor
public enum OperationType {

    PRIMARY(1),
    CHANGED(2),
    CANCELED(3);

    private final Integer code;

    public static OperationType getByCode(Integer code) {
        for (OperationType element : OperationType.values()) {
            if (element.getCode().equals(code)) return element;
        }
        throw new NoSuchElementException("No element for code " + code);
    }

    @JsonValue
    public Integer getCode() {
        return code;
    }
}
