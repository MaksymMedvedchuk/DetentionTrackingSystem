package com.arestmanagement.core.converter;

import com.arestmanagement.core.domain.dto.ArrestRequestDto;
import com.arestmanagement.core.domain.enums.InternalIdentityDocumentType;
import com.arestmanagement.util.Pair;


public interface ExternalDataConverter {

    Pair<InternalIdentityDocumentType, String> convertExternalToInternalData(ArrestRequestDto request);
}
