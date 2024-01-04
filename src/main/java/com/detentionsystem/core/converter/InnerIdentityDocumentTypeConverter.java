package com.detentionsystem.core.converter;

import com.detentionsystem.core.domain.enums.InternalIdentityDocumentType;
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
