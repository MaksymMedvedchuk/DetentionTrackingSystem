package com.arestmanagement.util;

public enum IdentityDocumentStateSystem {

    STATE_TAX_SERVICE_TYPE_ID_21("UA_passport", "SS SS NNNNNN"),
    STATE_TAX_SERVICE_TYPE_ID_22("international passport", "SS NNNNNN"),
    BAILIFFS_SERVICE_TYPE_ID_70("UA_passport", "NNNNNN-SSSS"),
    BAILIFFS_SERVICE_TYPE_ID_80("international passport", "SNNNNNN.SS");

    private final String docIdentStateTaxService;
    private final String docIdentBailiffsService;
    private final String format;


    IdentityDocumentStateSystem(String docIdent, String format) {
        this.docIdentStateTaxService = docIdent;
        this.docIdentBailiffsService = docIdent;
        this.format = format;
    }

    public String getDocIdentStateTaxService() {
        return docIdentStateTaxService;
    }

    public String getDocIdentBailiffsService() {
        return docIdentBailiffsService;
    }

    public String getFormat() {
        return format;
    }
}
