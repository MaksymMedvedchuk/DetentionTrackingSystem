package com.arestmanagement.converter;

import com.arestmanagement.dto.ArrestRequestDto;
import com.arestmanagement.util.ExternalIdentityDocumentType;
import com.arestmanagement.util.InternalIdentityDocumentType;
import javafx.util.Pair;
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

