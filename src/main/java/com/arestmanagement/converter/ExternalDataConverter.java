package com.arestmanagement.converter;

import com.arestmanagement.dto.ArrestRequestDto;
import com.arestmanagement.util.InternalIdentityDocumentType;
import javafx.util.Pair;

public interface ExternalDataConverter {

    Pair<InternalIdentityDocumentType, String> convertExternalToInternalData(ArrestRequestDto request);
}
