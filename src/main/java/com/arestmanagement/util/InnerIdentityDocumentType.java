package com.arestmanagement.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
@AllArgsConstructor
public enum IdentityDocumentType {
    PASSPORT(1),
    FOREIGN_PASSPORT(2);

    private final Integer code;

    public static IdentityDocumentType getByCode(Integer code) {
        for (IdentityDocumentType element : IdentityDocumentType.values()) {
            if (element.getCode().equals(code)) return element;
        }
        throw new NoSuchElementException("No element for code " + code);
    }
}
