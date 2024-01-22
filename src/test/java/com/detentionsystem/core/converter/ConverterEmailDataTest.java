package com.detentionsystem.core.converter;

import com.detentionsystem.core.domain.dto.EmailDto;
import com.detentionsystem.core.domain.entity.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConverterEmailDataTest {

	@InjectMocks
	private ConvertEmailData convertEmailData;

	@Mock
	private ModelMapper modelMapper;

	private Email expectedEmail;

	private Email email;

	private EmailDto emailDto;

	private EmailDto expectedEmailDto;

	@BeforeEach
	void setUp() {
		expectedEmail = new Email();
		expectedEmailDto = new EmailDto();
		emailDto = new EmailDto();
		email = new Email();
	}

	@Test
	void shouldConvertToDatabaseColumn() {
		when(modelMapper.map(emailDto, Email.class)).thenReturn(expectedEmail);

		Email actualEmail = convertEmailData.convertToDatabaseColumn(expectedEmailDto);

		assertEquals(expectedEmail, actualEmail);
		assertNotNull(actualEmail, "Mapping result should not be null");
		verify(modelMapper, times(1)).map(emailDto, Email.class);
	}

	@Test
	void shouldConvertToEntityAttribute() {
		when(modelMapper.map(email, EmailDto.class)).thenReturn(expectedEmailDto);

		EmailDto actualEmailDto = convertEmailData.convertToEntityAttribute(expectedEmail);

		assertEquals(expectedEmailDto, actualEmailDto);
		assertNotNull(actualEmailDto, "Mapping result should not be null");
		verify(modelMapper, times(1)).map(email, EmailDto.class);
	}
}
