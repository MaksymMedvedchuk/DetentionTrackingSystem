package com.detentionsystem.core.converter;

import com.detentionsystem.core.domain.enums.InternalIdentityDocumentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class InnerIdentityDocumentTypeConverterTest {

	private static final Integer EXPECTED_RESULT = 1;

	private static final Integer CODE = 1;

	@InjectMocks
	private InnerIdentityDocumentTypeConverter converter;

	private InternalIdentityDocumentType internalIdentityDocumentType;

	private InternalIdentityDocumentType expectedResult;

	@BeforeEach
	void setUp() {
		internalIdentityDocumentType = InternalIdentityDocumentType.PASSPORT;
		expectedResult = InternalIdentityDocumentType.getByCode(CODE);
	}

	@Test
	void shouldConvertToDatabaseColumn() {
		final Integer actualResult = converter.convertToDatabaseColumn(internalIdentityDocumentType);

		assertEquals(actualResult, EXPECTED_RESULT);
		assertNotNull(actualResult, "Mapping result should not be null");
	}

	@Test
	void shouldConvertToEntityAttribute() {
		final InternalIdentityDocumentType actualType = converter.convertToEntityAttribute(CODE);

		assertEquals(actualType, expectedResult);
		assertNotNull(actualType, "Mapping result should not be null");
	}

}
