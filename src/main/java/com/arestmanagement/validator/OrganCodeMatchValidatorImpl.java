package com.arestmanagement.validator;

import com.arestmanagement.dto.ArrestRequestDto;
import com.arestmanagement.exception.ValidationException;
import com.arestmanagement.util.ExternalIdentityDocumentType;
import com.arestmanagement.util.OrganizationCode;
import org.springframework.stereotype.Component;

@Component
public class OrganCodeMatchValidatorImpl implements OrganCodeMatchValidator {

    public void validateOrganCodeMatch(ArrestRequestDto requestDto) {
        OrganizationCode organCode = requestDto.getOrganCode();
        ExternalIdentityDocumentType identDocType = requestDto.getIdentityDocumentDto().getType();
        String identDocSeries = requestDto.getIdentityDocumentDto().getNumberSeries();

        if (!identDocType.doesOrganizationCorrespond(organCode))
            throw new ValidationException("Document type doesn't match organ code");

        if (!identDocSeries.matches(identDocType.getFormat()))
            throw new ValidationException("Document number series doesn't match document type");
    }
}
