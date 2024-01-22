package com.detentionsystem.controller;

import com.detentionsystem.core.converter.DetentionConverter;
import com.detentionsystem.core.domain.dto.DetentionDto;
import com.detentionsystem.core.domain.dto.DetentionRequestDto;
import com.detentionsystem.core.domain.dto.ResponseDto;
import com.detentionsystem.core.domain.entity.Detention;
import com.detentionsystem.core.service.DetentionService;
import com.detentionsystem.core.service.DetentionTrackingService;
import com.detentionsystem.core.service.email.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DetentionControllerTest {

	private static final String DOC_NUM = "docNum";
	@InjectMocks
	private DetentionController detentionController;
	@Mock
	private DetentionTrackingService detentionTrackingService;
	@Mock
	private DetentionService detentionService;
	@Mock
	private DetentionConverter detentionConverter;
	@Mock
	private EmailService emailService;
	private DetentionRequestDto requestDto;
	private ResponseDto responseDto;
	private DetentionDto detentionDto;
	private Detention detention;

	@BeforeEach
	void setUp() {
		requestDto = mock(DetentionRequestDto.class);
		responseDto = new ResponseDto();
		detentionDto = mock(DetentionDto.class);
		detention = new Detention();
	}

	@Test
	void shouldSaveDetentionToPerson() {
		when(detentionTrackingService.processRequest(requestDto)).thenReturn(responseDto);
		when(requestDto.getDetentionDto()).thenReturn(detentionDto);

		final ResponseEntity<ResponseDto> response = detentionController.saveDetentionToPerson(requestDto);

		verify(detentionTrackingService).processRequest(requestDto);
		verify(emailService).sendNotificationToPerson(requestDto);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	@Test
	void shouldGetDetentionByDocNum() {
		when(detentionService.findByDocNum(DOC_NUM)).thenReturn(detention);
		when(detentionConverter.convertToDatabaseColumn(detention)).thenReturn(detentionDto);

		final ResponseEntity<DetentionDto> response = detentionController.getDetentionByDocNum(DOC_NUM);

		verify(detentionService).findByDocNum(DOC_NUM);
		verify(detentionConverter).convertToDatabaseColumn(detention);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}
}
