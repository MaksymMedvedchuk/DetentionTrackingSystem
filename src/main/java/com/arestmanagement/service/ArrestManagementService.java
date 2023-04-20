package com.arestmanagement.service;

import com.arestmanagement.dto.ArrestRequestDto;
import com.arestmanagement.dto.ResponseDto;

public interface ArrestManagementService {

     ResponseDto processRequest(ArrestRequestDto request);
}
