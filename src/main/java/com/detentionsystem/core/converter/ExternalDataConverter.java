package com.detentionsystem.core.converter;

import com.detentionsystem.core.domain.dto.DetentionRequestDto;
import com.detentionsystem.core.domain.enums.InternalIdentityDocumentType;
import com.detentionsystem.util.Pair;


public interface ExternalDataConverter {

    Pair<InternalIdentityDocumentType, String> convertExternalToInternalData(DetentionRequestDto request);
}
