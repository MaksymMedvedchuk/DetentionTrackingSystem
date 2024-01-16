package com.detentionsystem.core.service;

import com.detentionsystem.core.domain.dto.DetentionRequestDto;
import com.detentionsystem.core.domain.dto.ResponseDto;

public interface DetentionTrackingService {

     ResponseDto processRequest(DetentionRequestDto request);
}
