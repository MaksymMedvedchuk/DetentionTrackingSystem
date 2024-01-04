package com.detentionsystem.controller;

import com.detentionsystem.config.Constant;
import com.detentionsystem.core.converter.ConvertEmailData;
import com.detentionsystem.core.domain.dto.EmailDto;
import com.detentionsystem.core.domain.entity.Email;
import com.detentionsystem.core.service.email.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.detentionsystem.controller.EmailController.URL;

@Slf4j
@RestController
@RequestMapping(Constant.VersionApi.VERSION + URL)
public class EmailController {

	protected static final String URL = "/email";

	private final ConvertEmailData convertEmailData;

	private final EmailService emailService;

	public EmailController(
		final ConvertEmailData convertEmailData1,
		final EmailService emailService
	) {
		this.convertEmailData = convertEmailData1;
		this.emailService = emailService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Create new email for ADMIN ROLE")
	public void saveEmail(@RequestBody @Valid EmailDto emailDto) {
		Email currentEmail = convertEmailData.convertToDatabaseColumn(emailDto);
		Email savedEmail = emailService.saveEmail(currentEmail);
		log.info("In saveEmail: created a email with id: {}", emailDto.getId());
	}
}
