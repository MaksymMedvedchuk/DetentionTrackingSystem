package com.detentionsystem.core.service;

import com.detentionsystem.core.domain.dto.ArrestRequestDto;
import com.detentionsystem.core.domain.dto.ResponseDto;

public interface ArrestManagementService {

     ResponseDto processRequest(ArrestRequestDto request);
}
