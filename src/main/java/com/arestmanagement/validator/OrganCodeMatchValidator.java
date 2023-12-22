package com.arestmanagement.validator;

import com.arestmanagement.core.domain.dto.ArrestRequestDto;

public interface OrganCodeMatchValidator {

    void validateOrganCodeMatch(ArrestRequestDto requestDto);
}


