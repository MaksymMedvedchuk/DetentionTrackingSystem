package com.arestmanagement.controller;

import com.arestmanagement.config.Constant;
import com.arestmanagement.core.converter.ArrestDataConverter;
import com.arestmanagement.core.domain.dto.ArrestDto;
import com.arestmanagement.core.domain.dto.ArrestRequestDto;
import com.arestmanagement.core.domain.dto.ResponseDto;
import com.arestmanagement.core.domain.entity.Arrest;
import com.arestmanagement.core.service.ArrestManagementService;
import com.arestmanagement.core.service.ArrestService;
import com.arestmanagement.core.service.email.EmailService;
import com.arestmanagement.validator.UuidValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.arestmanagement.controller.ArrestManagementController.URL;

@Slf4j
@RestController
@RequestMapping(Constant.VersionApi.VERSION + URL)
@SecurityRequirement(name = Constant.Token.AUTHORIZATION_HEADER)
public class ArrestManagementController {

	protected static final String URL = "/arrestManagement";
	private final ArrestManagementService arrestManagementService;

	private final ArrestService arrestService;

	private final ArrestDataConverter arrestDataConverter;

	private final EmailService emailService;

	public ArrestManagementController(
		final ArrestManagementService arrestManagementService,
		final ArrestService arrestService,
		final ArrestDataConverter arrestDataConverter,
		final EmailService emailService

	) {
		this.arrestManagementService = arrestManagementService;
		this.arrestService = arrestService;
		this.arrestDataConverter = arrestDataConverter;
		this.emailService = emailService;
	}

	@Operation(summary = "Save arrest for persone")
	@PostMapping("/create_arrest")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ResponseDto> saveArrestToPerson(@UuidValidator @Valid @RequestBody final ArrestRequestDto request) {
		ResponseDto result = arrestManagementService.processRequest(request);
		log.info("In saveArrestToPerson saved arrest with docNum: [{}]", request.getArrestDto().getDocNum());

		emailService.sendNotificationToPerson(request);
		log.info("Notification about arrest was sent to email: [{}]", request.getEmail());

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/get_arrest/{docNum}")
	@Operation(summary = "Get arrest by docNum")
	@PreAuthorize("hasRole('ADMIN') or hasRole('PERSONE')")
	public ResponseEntity<ArrestDto> getArrestDocNum(@PathVariable final String docNum) {
		Arrest arrest = arrestService.findByDocNum(docNum);
		ArrestDto result = arrestDataConverter.convertToDatabaseColumn(arrest);

		log.info("In getArrestDocNum found arrest by socNum: [{}]", result.getDocNum());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}


