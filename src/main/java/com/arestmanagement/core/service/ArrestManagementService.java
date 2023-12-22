package com.arestmanagement.core.service;

import com.arestmanagement.core.domain.dto.ArrestRequestDto;
import com.arestmanagement.core.domain.dto.ResponseDto;

public interface ArrestManagementService {

     ResponseDto processRequest(ArrestRequestDto request);
}
