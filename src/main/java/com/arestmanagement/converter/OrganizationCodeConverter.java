package com.arestmanagement.converter;

import com.arestmanagement.util.StateOrganizationType;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

@Convert
public class StateOrganizationTypeConverter implements AttributeConverter<StateOrganizationType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(StateOrganizationType attribute) {
        return attribute.getCode();
    }

    @Override
    public StateOrganizationType convertToEntityAttribute(Integer dbData) {
        return StateOrganizationType.getByCode(dbData);
    }
}
