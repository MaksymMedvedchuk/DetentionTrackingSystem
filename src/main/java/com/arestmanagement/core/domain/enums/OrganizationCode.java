package com.arestmanagement.core.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
@AllArgsConstructor
public enum OrganizationCode {

    SERVICE_OF_BAILIFFS(17),
    STATE_TAX_SERVICE(39);

    private final Integer code;

    public static OrganizationCode getByCode(Integer code) {
        for (OrganizationCode element : OrganizationCode.values()) {
            if (element.getCode().equals(code)) return element;
        }
        throw new NoSuchElementException("No element for code " + code);
    }

    @JsonValue
    public Integer getCode() {
        return code;
    }
}

