package com.arestmanagement.util;

import com.arestmanagement.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
@AllArgsConstructor
public enum InternalIdentityDocumentType {


    PASSPORT(1, InternalIdentityDocumentType.PASSPORT_FORMAT),
    FOREIGN_PASSPORT(2, InternalIdentityDocumentType.FOREIGN_PASSPORT_FORMAT);

    private static final String PASSPORT_FORMAT = "([0-9]{6})\\s([0-9]{2})\\s([0-9]{2})";
    private static final String FOREIGN_PASSPORT_FORMAT = "([0-9]{6})\\s([0-9]{2})";
    private final Integer code;
    private final String format;

    public static InternalIdentityDocumentType getByCode(Integer code) {
        for (InternalIdentityDocumentType element : InternalIdentityDocumentType.values()) {
            if (element.getCode().equals(code)) return element;
        }
        throw new ValidationException("No element for code " + code);
    }

    @JsonValue
    public Integer getCode() {
        return code;
    }
}
