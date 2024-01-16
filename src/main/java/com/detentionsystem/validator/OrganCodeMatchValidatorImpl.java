package com.detentionsystem.validator;

import com.detentionsystem.core.domain.dto.DetentionRequestDto;
import com.detentionsystem.core.exception.ValidationException;
import com.detentionsystem.core.domain.enums.ExternalIdentityDocumentType;
import com.detentionsystem.core.domain.enums.OrganizationCode;
import org.springframework.stereotype.Component;

@Component
public class OrganCodeMatchValidatorImpl implements OrganCodeMatchValidator {

    public void validateOrganCodeMatch(DetentionRequestDto requestDto) {
        OrganizationCode organCode = requestDto.getOrganCode();
        ExternalIdentityDocumentType identDocType = requestDto.getIdentityDocumentDto().getType();
        String identDocSeries = requestDto.getIdentityDocumentDto().getNumberSeries();

        if (!identDocType.doesOrganizationCorrespond(organCode))
            throw new ValidationException("Document type doesn't match organ code");

        if (!identDocSeries.matches(identDocType.getFormat()))
            throw new ValidationException("Document number series doesn't match document type");
    }
}
