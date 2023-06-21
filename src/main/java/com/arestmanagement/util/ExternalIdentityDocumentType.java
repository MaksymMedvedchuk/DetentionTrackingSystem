package com.arestmanagement.util;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
@AllArgsConstructor
public enum ExternalIdentityDocumentType {

    STATE_TAX_SERVICE_PASSPORT(21, ExternalIdentityDocumentType.STATE_TAX_SERVICE_PASSPORT_FORMAT,
            OrganizationCode.STATE_TAX_SERVICE, "$3 $2 $1", InternalIdentityDocumentType.PASSPORT),
    STATE_TAX_SERVICE_FOREIGN_PASSPORT(22, ExternalIdentityDocumentType.STATE_TAX_SERVICE_FOREIGN_PASSPORT_FORMAT,
            OrganizationCode.STATE_TAX_SERVICE, "$2 $1", InternalIdentityDocumentType.FOREIGN_PASSPORT),
    SERVICE_OF_BAILIFFS_PASSPORT(70, ExternalIdentityDocumentType.SERVICE_OF_BAILIFFS_PASSPORT_FORMAT,
            OrganizationCode.SERVICE_OF_BAILIFFS, "$1 $2 $3", InternalIdentityDocumentType.PASSPORT),
    SERVICE_OF_BAILIFFS_FOREIGN_PASSPORT(80, ExternalIdentityDocumentType.SERVICE_OF_BAILIFFS_FOREIGN_PASSPORT_FORMAT,
            OrganizationCode.SERVICE_OF_BAILIFFS, "$1 $2", InternalIdentityDocumentType.FOREIGN_PASSPORT);

    private static final String STATE_TAX_SERVICE_PASSPORT_FORMAT = "([0-9]{2})\\s([0-9]{2})\\s([0-9]{6})";
    private static final String STATE_TAX_SERVICE_FOREIGN_PASSPORT_FORMAT = "([0-9]{2})\\s([0-9]{6})";
    private static final String SERVICE_OF_BAILIFFS_PASSPORT_FORMAT = "([0-9]{6})\\-([0-9]{2})([0-9]{2})";
    private static final String SERVICE_OF_BAILIFFS_FOREIGN_PASSPORT_FORMAT = "([0-9]{6})\\.([0-9]{2})";

    private final Integer code;
    private final String format;
    private final OrganizationCode organCode;
    private final String replacer;
    private final InternalIdentityDocumentType internalType;

    public boolean doesOrganizationCorrespond(OrganizationCode organCode) {
        return this.organCode.equals(organCode);
    }

    public String getInternalSeries(String externalSeries) {
        if (externalSeries.matches(this.format)) return externalSeries.replaceAll(this.format, this.replacer);
        throw new NoSuchElementException("You specified the wrong number series");
    }

    @JsonValue
    public Integer getCode() {
        return code;
    }
}

