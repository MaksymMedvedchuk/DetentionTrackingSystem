package com.detentionsystem.core.converter;

import com.detentionsystem.core.domain.dto.DetentionDto;
import com.detentionsystem.core.domain.entity.Detention;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DetentionConverterTest {

	@InjectMocks
	private DetentionConverter detentionConverter;

	@Mock
	private ModelMapper modelMapper;

	private Detention detention;

	private DetentionDto expectedDetentionDto;

	private DetentionDto detentionDto;

	private Detention expectedDetention;

	@BeforeEach
	void setUp() {
		detention = new Detention();
		expectedDetentionDto = new DetentionDto();
		detentionDto = new DetentionDto();
		expectedDetention= new Detention();
	}

	@Test
	void shouldConvertToDatabaseColumn() {
		when(modelMapper.map(detention, DetentionDto.class)).thenReturn(expectedDetentionDto);

		final DetentionDto actualDetentionDto = detentionConverter.convertToDatabaseColumn(detention);

		assertEquals(actualDetentionDto, expectedDetentionDto);
		assertNotNull(actualDetentionDto, "Mapping result should not be null");
		verify(modelMapper).map(detention, DetentionDto.class);
	}

	@Test
	void shouldConvertToEntityAttribute() {
		when(modelMapper.map(detentionDto, Detention.class)).thenReturn(expectedDetention);

		final Detention actualDetention = detentionConverter.convertToEntityAttribute(detentionDto);

		assertEquals(actualDetention, expectedDetention);
		assertNotNull(actualDetention, "Mapping result should not be null");
		verify(modelMapper).map(detentionDto, Detention.class);
	}
}
