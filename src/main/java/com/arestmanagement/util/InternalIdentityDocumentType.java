package com.arestmanagement.util;

import com.arestmanagement.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
@AllArgsConstructor
public enum InternalIdentityDocumentType {


    PASSPORT(1),
    FOREIGN_PASSPORT(2);

    private final Integer code;

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
