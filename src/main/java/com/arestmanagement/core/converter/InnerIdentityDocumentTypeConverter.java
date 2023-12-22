package com.arestmanagement.core.converter;

import com.arestmanagement.core.domain.enums.InternalIdentityDocumentType;
import jakarta.persistence.AttributeConverter;

public class InnerIdentityDocumentTypeConverter implements AttributeConverter<InternalIdentityDocumentType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(InternalIdentityDocumentType attribute) {
        return attribute.getCode();
    }

    @Override
    public InternalIdentityDocumentType convertToEntityAttribute(Integer dbData) {
        return InternalIdentityDocumentType.getByCode(dbData);
    }
}
