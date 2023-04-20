package com.arestmanagement.util;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum OrganizationCode {

    SERVICE_OF_BAILIFFS(17),
    STATE_TAX_SERVICE(39);

    private final Integer code;

    public static OrganizationCode getByCode(Integer code) {//перебирає значення поки не знайде співпадіння
        for (OrganizationCode element : OrganizationCode.values()) {
            if (element.getCode().equals(code)) return element;
        }
        throw new NoSuchElementException("No element for code " + code);
    }


    @JsonValue//серіалізує значення в json
    public Integer getCode() {
        return code;
    }
}

