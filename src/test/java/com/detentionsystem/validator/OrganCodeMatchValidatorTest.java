package com.detentionsystem.validator;

import com.detentionsystem.core.domain.dto.DetentionRequestDto;
import com.detentionsystem.core.domain.dto.IdentityDocumentDto;
import com.detentionsystem.core.exception.ValidationException;
import com.detentionsystem.core.domain.enums.ExternalIdentityDocumentType;
import com.detentionsystem.core.domain.enums.OrganizationCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class OrganCodeMatchValidatorTest {

    @InjectMocks
    private OrganCodeMatchValidatorImpl mockOrganCode;

    @Test
    public void testIsValidateOrganCodeMatch() {
        DetentionRequestDto request = buildRequestDto(OrganizationCode.STATE_TAX_SERVICE);
        assertDoesNotThrow(() -> mockOrganCode.validateOrganCodeMatch(request));
    }

    @Test
    public void testIsNotValidateOrganCodeMatch() {
        DetentionRequestDto request = buildRequestDto(OrganizationCode.SERVICE_OF_BAILIFFS);
        assertThrows(ValidationException.class, () -> mockOrganCode.validateOrganCodeMatch(request));
    }

    private DetentionRequestDto buildRequestDto(OrganizationCode stateTaxService) {
        DetentionRequestDto request = new DetentionRequestDto();
        IdentityDocumentDto identityDocumentDto = new IdentityDocumentDto();
        ExternalIdentityDocumentType identDocType = ExternalIdentityDocumentType.STATE_TAX_SERVICE_PASSPORT;
        String identDocSeries = "11 22 333333";
        identityDocumentDto.setType(identDocType);
        identityDocumentDto.setNumberSeries(identDocSeries);
        request.setOrganCode(stateTaxService);
        request.setIdentityDocumentDto(identityDocumentDto);
        return request;
    }
}

