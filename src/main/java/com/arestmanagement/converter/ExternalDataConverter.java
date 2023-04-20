package com.arestmanagement.converter;

import com.arestmanagement.dto.ArrestRequestDto;
import com.arestmanagement.util.InternalIdentityDocumentType;
import javafx.util.Pair;

//todo java naming convention
public interface ExternalDataConverter {

     Pair<InternalIdentityDocumentType, String> convertExternalToInternalData(ArrestRequestDto request);
}
