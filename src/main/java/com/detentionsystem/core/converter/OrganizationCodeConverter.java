package com.detentionsystem.core.converter;

import com.detentionsystem.core.domain.enums.OrganizationCode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

@Convert
public class OrganizationCodeConverter implements AttributeConverter<OrganizationCode, Integer> {
    @Override
    public Integer convertToDatabaseColumn(OrganizationCode attribute) {
        return attribute.getCode();
    }

    @Override
    public OrganizationCode convertToEntityAttribute(Integer dbData) {
        return OrganizationCode.getByCode(dbData);
    }
}
