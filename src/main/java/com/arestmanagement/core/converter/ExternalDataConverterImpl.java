package com.arestmanagement.core.converter;

import com.arestmanagement.core.domain.dto.ArrestRequestDto;
import com.arestmanagement.core.domain.enums.ExternalIdentityDocumentType;
import com.arestmanagement.core.domain.enums.InternalIdentityDocumentType;
import com.arestmanagement.util.Pair;
import org.springframework.stereotype.Component;

@Component
public class ExternalDataConverterImpl implements ExternalDataConverter {

    public Pair<InternalIdentityDocumentType, String> convertExternalToInternalData(ArrestRequestDto request) {
        ExternalIdentityDocumentType externalType = request.getIdentityDocumentDto().getType();
        String externalSeries = request.getIdentityDocumentDto().getNumberSeries();
        InternalIdentityDocumentType internalType = externalType.getInternalType();
        String internalFormat = externalType.getInternalSeries(externalSeries);
        return new Pair<>(internalType, internalFormat);
    }
}

