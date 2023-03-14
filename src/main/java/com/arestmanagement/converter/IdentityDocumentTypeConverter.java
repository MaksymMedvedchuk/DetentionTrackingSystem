package com.example.customerarrestsystem.converter;

import com.example.customerarrestsystem.util.IdentityDocumentType;

import javax.persistence.AttributeConverter;

public class IdentityDocumentTypeConverter implements AttributeConverter<IdentityDocumentType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(IdentityDocumentType attribute) {
        return attribute.getCode();
    }

    @Override
    public IdentityDocumentType convertToEntityAttribute(Integer dbData) {
        return IdentityDocumentType.getByCode(dbData);
    }
}
