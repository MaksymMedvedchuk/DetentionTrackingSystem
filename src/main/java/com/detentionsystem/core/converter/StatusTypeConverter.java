package com.detentionsystem.core.converter;

import com.detentionsystem.core.domain.enums.StatusType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

@Convert
public class StatusTypeConverter implements AttributeConverter<StatusType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(StatusType attribute) {
        return attribute.getCode();
    }

    @Override
    public StatusType convertToEntityAttribute(Integer dbData) {
        return StatusType.getByCode(dbData);
    }
}
