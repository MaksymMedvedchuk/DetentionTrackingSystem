package com.detentionsystem.core.converter;

import com.detentionsystem.core.domain.dto.ArrestRequestDto;
import com.detentionsystem.core.domain.enums.ExternalIdentityDocumentType;
import com.detentionsystem.core.domain.enums.InternalIdentityDocumentType;
import com.detentionsystem.util.Pair;
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

