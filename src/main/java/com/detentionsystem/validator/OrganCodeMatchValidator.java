package com.detentionsystem.validator;

import com.detentionsystem.core.domain.dto.ArrestRequestDto;

public interface OrganCodeMatchValidator {

    void validateOrganCodeMatch(ArrestRequestDto requestDto);
}


