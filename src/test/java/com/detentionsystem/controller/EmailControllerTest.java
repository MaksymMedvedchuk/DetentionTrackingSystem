package com.detentionsystem.controller;

import com.detentionsystem.core.converter.ConvertEmailData;
import com.detentionsystem.core.domain.dto.EmailDto;
import com.detentionsystem.core.domain.entity.Email;
import com.detentionsystem.core.service.email.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailControllerTest {

	@InjectMocks
	private EmailController emailController;

	@Mock
	private ConvertEmailData convertEmailData;

	@Mock
	private EmailService emailService;

	private EmailDto emailDto;

	private Email email;

	@BeforeEach
	void setUp(){
		emailDto = new EmailDto();
		email = new Email();
	}

	@Test
	void shouldSaveEmail() {
		when(convertEmailData.convertToDatabaseColumn(emailDto)).thenReturn(email);
		when(emailService.saveEmail(email)).thenReturn(email);

		emailController.saveEmail(emailDto);

		verify(emailService, times(1)).saveEmail(email);
		verify(convertEmailData, times(1)).convertToDatabaseColumn(emailDto);
	}
}
