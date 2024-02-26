package com.detentionsystem.controller;

import com.detentionsystem.config.Constant;
import com.detentionsystem.core.converter.DetentionConverter;
import com.detentionsystem.core.domain.dto.DetentionDto;
import com.detentionsystem.core.domain.dto.DetentionRequestDto;
import com.detentionsystem.core.domain.dto.ResponseDto;
import com.detentionsystem.core.domain.entity.Detention;
import com.detentionsystem.core.service.DetentionService;
import com.detentionsystem.core.service.DetentionTrackingService;
import com.detentionsystem.core.service.email.EmailService;
import com.detentionsystem.validator.UuidValidator;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.detentionsystem.controller.DetentionController.URL;

@Slf4j
@RestController
@RequestMapping(Constant.VersionApi.VERSION + URL)
@SecurityRequirement(name = Constant.Token.AUTHORIZATION_HEADER)
public class DetentionController {

	protected static final String URL = "/detention";
	private final DetentionTrackingService detentionTrackingService;

	private final DetentionService detentionService;

	private final DetentionConverter detentionConverter;

	private final EmailService emailService;

	public DetentionController(
		final DetentionTrackingService detentionTrackingService,
		final DetentionService detentionService,
		final DetentionConverter detentionConverter,
		final EmailService emailService

	) {
		this.detentionTrackingService = detentionTrackingService;
		this.detentionService = detentionService;
		this.detentionConverter = detentionConverter;
		this.emailService = emailService;
	}

	@Operation(summary = "Save detention for person")
	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ResponseDto> saveDetentionToPerson(@UuidValidator @Valid @RequestBody final DetentionRequestDto request) {
		ResponseDto result = detentionTrackingService.processRequest(request);
		log.info("In saveDetentionToPerson saved detention with docNum: [{}]", request.getDetentionDto().getDocNum());

		emailService.sendNotificationToPerson(request);
		log.info("Notification about detention was sent to email: [{}]", request.getEmail());

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/get/{docNum}")
	@Operation(summary = "Get detention by docNum")
	@PreAuthorize("hasRole('ADMIN') or hasRole('PERSONE')")
	public ResponseEntity<DetentionDto> getDetentionByDocNum(@PathVariable final String docNum) {
		Detention detention = detentionService.findByDocNum(docNum);
		DetentionDto result = detentionConverter.convertToDatabaseColumn(detention);

		log.info("In getDetentionDocNum found detention by docNum: [{}]", result.getDocNum());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/payOff")
	@Operation(summary = "pay off fine amount")
	@PreAuthorize("hasRole('PERSONE')")
	public ResponseEntity<String> payOffFineAmount(@RequestParam String docNum, @RequestParam Long fineAmount) {
		detentionService.payOffFineAmount(docNum, fineAmount);

		log.info("In payOffFineAmount fine amount of was paid off of docNum: [{}]", docNum);
		return new ResponseEntity<>("successful", HttpStatus.OK);
	}


}


