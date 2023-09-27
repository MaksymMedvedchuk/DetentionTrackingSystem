package com.arestmanagement.converter;

import com.arestmanagement.dto.ArrestRequestDto;
import com.arestmanagement.util.InternalIdentityDocumentType;
import com.arestmanagement.util.Pair;


public interface ExternalDataConverter {

    Pair<InternalIdentityDocumentType, String> convertExternalToInternalData(ArrestRequestDto request);
}
