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

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ExternalDataConverterTest {
    //todo в чому різниця Mock та InjectMocks
    //todo inject via interface
    @InjectMocks
    private ExternalDataConverterImpl externalDataConverter;

    @Test
    //todo test other doc types
    public void testConvertExternalToInternalDataServiceOfBailiffsPassport() {
        IdentityDocumentDto identDocDto = new IdentityDocumentDto();
        ArrestRequestDto arrestRequestDto = new ArrestRequestDto();
        ExternalIdentityDocumentType externalIdentityDocumentType = ExternalIdentityDocumentType.SERVICE_OF_BAILIFFS_PASSPORT;
        String externalNumSeries = "111111-6666";
        identDocDto.setType(externalIdentityDocumentType);
        identDocDto.setNumberSeries(externalNumSeries);
        arrestRequestDto.setIdentityDocumentDto(identDocDto);
        Pair<InternalIdentityDocumentType, String> internalIdentDoc = externalDataConverter.convertExternalToInternalData(arrestRequestDto);
        assertEquals(InternalIdentityDocumentType.PASSPORT, internalIdentDoc.getKey());
        assertEquals("111111 66 66", internalIdentDoc.getValue());
    }

    @Test
    public void testConvertExternalToInternalDataServiceOfBailiffsForeignPassport() {
        IdentityDocumentDto identDocDto = new IdentityDocumentDto();
        ArrestRequestDto arrestRequestDto = new ArrestRequestDto();
        ExternalIdentityDocumentType externalIdentityDocumentType = ExternalIdentityDocumentType.SERVICE_OF_BAILIFFS_FOREIGN_PASSPORT;
        String externalNumSeries = "111111.22";
        identDocDto.setType(externalIdentityDocumentType);
        identDocDto.setNumberSeries(externalNumSeries);
        arrestRequestDto.setIdentityDocumentDto(identDocDto);
        Pair<InternalIdentityDocumentType, String> internalIdentDoc = externalDataConverter.convertExternalToInternalData(arrestRequestDto);
        assertEquals(InternalIdentityDocumentType.FOREIGN_PASSPORT, internalIdentDoc.getKey());
        assertEquals("111111 22", internalIdentDoc.getValue());
    }

    @Test
    public void testConvertExternalToInternalDataStateTaxServicePassport() {
        IdentityDocumentDto identDocDto = new IdentityDocumentDto();
        ArrestRequestDto arrestRequestDto = new ArrestRequestDto();
        ExternalIdentityDocumentType externalIdentityDocumentType = ExternalIdentityDocumentType.STATE_TAX_SERVICE_PASSPORT;
        String externalNumSeries = "11 22 333333";
        identDocDto.setType(externalIdentityDocumentType);
        identDocDto.setNumberSeries(externalNumSeries);
        arrestRequestDto.setIdentityDocumentDto(identDocDto);
        Pair<InternalIdentityDocumentType, String> internalIdentDoc = externalDataConverter.convertExternalToInternalData(arrestRequestDto);
        assertEquals(InternalIdentityDocumentType.PASSPORT, internalIdentDoc.getKey());
        assertEquals("333333 22 11", internalIdentDoc.getValue());
    }

    @Test
    public void testConvertExternalToInternalDataStateTaxServiceForeignPassport() {
        IdentityDocumentDto identDocDto = new IdentityDocumentDto();
        ArrestRequestDto arrestRequestDto = new ArrestRequestDto();
        ExternalIdentityDocumentType externalIdentityDocumentType = ExternalIdentityDocumentType.STATE_TAX_SERVICE_FOREIGN_PASSPORT;
        String externalNumSeries = "11 222222";
        identDocDto.setType(externalIdentityDocumentType);
        identDocDto.setNumberSeries(externalNumSeries);
        arrestRequestDto.setIdentityDocumentDto(identDocDto);
        Pair<InternalIdentityDocumentType, String> internalIdentDoc = externalDataConverter.convertExternalToInternalData(arrestRequestDto);
        assertEquals(InternalIdentityDocumentType.FOREIGN_PASSPORT, internalIdentDoc.getKey());
        assertEquals("222222 11", internalIdentDoc.getValue());
    }
}
