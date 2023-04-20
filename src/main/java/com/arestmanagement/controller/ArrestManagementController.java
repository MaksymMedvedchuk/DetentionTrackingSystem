package com.arestmanagement.controller;


import com.arestmanagement.dto.ArrestRequestDto;
import com.arestmanagement.dto.ResponseDto;
import com.arestmanagement.service.ArrestManagementService;
import com.arestmanagement.validator.UuidValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/arrestManagement/")
public class ArrestManagementController {

    @Autowired
    private ArrestManagementService arrestManagementService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ResponseDto> savePerson(@UuidValidator @Valid @RequestBody ArrestRequestDto request) {
        ResponseDto result = arrestManagementService.processRequest(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}


