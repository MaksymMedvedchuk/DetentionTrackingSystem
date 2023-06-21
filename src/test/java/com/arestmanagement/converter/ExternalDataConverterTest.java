package com.arestmanagement.converter;

import com.arestmanagement.dto.ArrestRequestDto;
import com.arestmanagement.dto.IdentityDocumentDto;
import com.arestmanagement.util.ExternalIdentityDocumentType;
import com.arestmanagement.util.InternalIdentityDocumentType;
import javafx.util.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class ExternalDataConverterTest {

    @InjectMocks
    private ExternalDataConverterImpl externalDataConverter;

    @Test
    public void testConvertExternalToInternalDataServiceOfBailiffsPassport() {
        ArrestRequestDto arrestRequestDto = buildRequestDto(ExternalIdentityDocumentType.SERVICE_OF_BAILIFFS_PASSPORT, "111111-6666");
        Pair<InternalIdentityDocumentType, String> internalIdentDoc = externalDataConverter.convertExternalToInternalData(arrestRequestDto);
        assertEquals(InternalIdentityDocumentType.PASSPORT, internalIdentDoc.getKey());
        assertEquals("111111 66 66", internalIdentDoc.getValue());
    }

    @Test
    public void testConvertExternalToInternalDataServiceOfBailiffsForeignPassport() {
        ArrestRequestDto arrestRequestDto = buildRequestDto(ExternalIdentityDocumentType.SERVICE_OF_BAILIFFS_FOREIGN_PASSPORT, "111111.22");
        Pair<InternalIdentityDocumentType, String> internalIdentDoc = externalDataConverter.convertExternalToInternalData(arrestRequestDto);
        assertEquals(InternalIdentityDocumentType.FOREIGN_PASSPORT, internalIdentDoc.getKey());
        assertEquals("111111 22", internalIdentDoc.getValue());
    }

    @Test
    public void testConvertExternalToInternalDataStateTaxServicePassport() {
        ArrestRequestDto arrestRequestDto = buildRequestDto(ExternalIdentityDocumentType.STATE_TAX_SERVICE_PASSPORT, "11 22 333333");
        Pair<InternalIdentityDocumentType, String> internalIdentDoc = externalDataConverter.convertExternalToInternalData(arrestRequestDto);
        assertEquals(InternalIdentityDocumentType.PASSPORT, internalIdentDoc.getKey());
        assertEquals("333333 22 11", internalIdentDoc.getValue());
    }

    @Test
    public void testConvertExternalToInternalDataStateTaxServiceForeignPassport() {
        ArrestRequestDto arrestRequestDto = buildRequestDto(ExternalIdentityDocumentType.STATE_TAX_SERVICE_FOREIGN_PASSPORT, "11 222222");
        Pair<InternalIdentityDocumentType, String> internalIdentDoc = externalDataConverter.convertExternalToInternalData(arrestRequestDto);
        assertEquals(InternalIdentityDocumentType.FOREIGN_PASSPORT, internalIdentDoc.getKey());
        assertEquals("222222 11", internalIdentDoc.getValue());
    }

    @Test
    public void testConvertExternalToInternalFormatMismatch() {
        ArrestRequestDto arrestRequestDto = buildRequestDto(ExternalIdentityDocumentType.STATE_TAX_SERVICE_FOREIGN_PASSPORT, "111111.22");
        assertThrows(NoSuchElementException.class, () -> externalDataConverter.convertExternalToInternalData(arrestRequestDto));
    }

    private ArrestRequestDto buildRequestDto(ExternalIdentityDocumentType externalIdentityDocumentType, String externalNumSeries) {
        IdentityDocumentDto identDocDto = new IdentityDocumentDto();
        ArrestRequestDto arrestRequestDto = new ArrestRequestDto();
        identDocDto.setType(externalIdentityDocumentType);
        identDocDto.setNumberSeries(externalNumSeries);
        arrestRequestDto.setIdentityDocumentDto(identDocDto);
        return arrestRequestDto;
    }
}
