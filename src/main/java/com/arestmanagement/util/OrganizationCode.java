package com.arestmanagement.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
@AllArgsConstructor
public enum StateOrganizationType {

    STATE_TAX_SERVICE(39),
    SERVICE_OF_BAILIFFS(17);

    private final Integer code;

    public static StateOrganizationType getByCode(Integer code) {//перебирає значення поки не знайде співпадіння
        for (StateOrganizationType element : StateOrganizationType.values()) {
            if (element.getCode().equals(code)) return element;
        }
        throw new NoSuchElementException("No element for code " + code);
    }
}

