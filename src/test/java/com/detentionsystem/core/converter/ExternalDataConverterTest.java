package com.detentionsystem.core.converter;

import com.detentionsystem.core.domain.dto.DetentionRequestDto;
import com.detentionsystem.core.domain.dto.IdentityDocumentDto;
import com.detentionsystem.core.domain.enums.ExternalIdentityDocumentType;
import com.detentionsystem.core.domain.enums.InternalIdentityDocumentType;
import com.detentionsystem.util.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class ExternalDataConverterTest {

	@InjectMocks
	private ExternalDataConverterImpl externalDataConverter;

	@Test
	public void testConvertExternalToInternalDataServiceOfBailiffsPassport() {
		DetentionRequestDto
			detentionRequestDto =
			buildRequestDto(ExternalIdentityDocumentType.SERVICE_OF_BAILIFFS_PASSPORT, "111111-6666");
		Pair<InternalIdentityDocumentType, String>
			internalIdentDoc =
			externalDataConverter.convertExternalToInternalData(detentionRequestDto);
		assertEquals(InternalIdentityDocumentType.PASSPORT, internalIdentDoc.getKey());
		assertEquals("111111 66 66", internalIdentDoc.getValue());
	}

	@Test
	public void testConvertExternalToInternalDataServiceOfBailiffsForeignPassport() {
		DetentionRequestDto
			detentionRequestDto =
			buildRequestDto(ExternalIdentityDocumentType.SERVICE_OF_BAILIFFS_FOREIGN_PASSPORT, "111111.22");
		Pair<InternalIdentityDocumentType, String>
			internalIdentDoc =
			externalDataConverter.convertExternalToInternalData(detentionRequestDto);
		assertEquals(InternalIdentityDocumentType.FOREIGN_PASSPORT, internalIdentDoc.getKey());
		assertEquals("111111 22", internalIdentDoc.getValue());
	}

	@Test
	public void testConvertExternalToInternalDataStateTaxServicePassport() {
		DetentionRequestDto
			detentionRequestDto =
			buildRequestDto(ExternalIdentityDocumentType.STATE_TAX_SERVICE_PASSPORT, "11 22 333333");
		Pair<InternalIdentityDocumentType, String>
			internalIdentDoc =
			externalDataConverter.convertExternalToInternalData(detentionRequestDto);
		assertEquals(InternalIdentityDocumentType.PASSPORT, internalIdentDoc.getKey());
		assertEquals("333333 22 11", internalIdentDoc.getValue());
	}

	@Test
	public void testConvertExternalToInternalDataStateTaxServiceForeignPassport() {
		DetentionRequestDto
			detentionRequestDto =
			buildRequestDto(ExternalIdentityDocumentType.STATE_TAX_SERVICE_FOREIGN_PASSPORT, "11 222222");
		Pair<InternalIdentityDocumentType, String>
			internalIdentDoc =
			externalDataConverter.convertExternalToInternalData(detentionRequestDto);
		assertEquals(InternalIdentityDocumentType.FOREIGN_PASSPORT, internalIdentDoc.getKey());
		assertEquals("222222 11", internalIdentDoc.getValue());
	}

	@Test
	public void testConvertExternalToInternalFormatMismatch() {
		DetentionRequestDto
			detentionRequestDto =
			buildRequestDto(ExternalIdentityDocumentType.STATE_TAX_SERVICE_FOREIGN_PASSPORT, "111111.22");
		assertThrows(
			NoSuchElementException.class,
			() -> externalDataConverter.convertExternalToInternalData(detentionRequestDto)
		);
	}

	private DetentionRequestDto buildRequestDto(
		ExternalIdentityDocumentType externalIdentityDocumentType,
		String externalNumSeries
	) {
		IdentityDocumentDto identDocDto = new IdentityDocumentDto();
		DetentionRequestDto detentionRequestDto = new DetentionRequestDto();
		identDocDto.setType(externalIdentityDocumentType);
		identDocDto.setNumberSeries(externalNumSeries);
		detentionRequestDto.setIdentityDocumentDto(identDocDto);
		return detentionRequestDto;
	}
}
