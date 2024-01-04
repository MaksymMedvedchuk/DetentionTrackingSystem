package com.detentionsystem.core.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResultCode {

    SUCCESS(0),
    BUSINESS_DATA_ERROR(3),
    TECHNICAL_ERROR(5);

    private final Integer resultCode;

    @JsonValue
    public Integer getResultCode() {
        return resultCode;
    }
}

