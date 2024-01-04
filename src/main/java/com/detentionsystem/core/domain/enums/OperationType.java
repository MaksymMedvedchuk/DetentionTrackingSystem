package com.detentionsystem.core.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationType {

    PRIMARY(1),
    CHANGED(2),
    CANCELED(3);

    private final Integer code;

    @JsonValue
    public Integer getCode() {
        return code;
    }
}
