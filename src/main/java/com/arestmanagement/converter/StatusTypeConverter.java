package com.arestmanagement.converter;

import com.arestmanagement.util.StatusType;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

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
