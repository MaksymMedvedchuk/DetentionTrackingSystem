package com.detentionsystem.validator;

import com.detentionsystem.core.domain.dto.DetentionRequestDto;

public interface OrganCodeMatchValidator {

    void validateOrganCodeMatch(DetentionRequestDto requestDto);
}


