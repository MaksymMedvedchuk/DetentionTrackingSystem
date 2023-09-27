package com.arestmanagement.controller;

import com.arestmanagement.dto.ArrestRequestDto;
import com.arestmanagement.dto.ResponseDto;
import com.arestmanagement.service.ArrestManagementService;
import com.arestmanagement.validator.UuidValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/arrestManagement/")
public class ArrestManagementController {

	@Autowired
	private ArrestManagementService arrestManagementService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<ResponseDto> savePerson(@UuidValidator @Valid @RequestBody ArrestRequestDto request) {
		ResponseDto result = arrestManagementService.processRequest(request);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}


