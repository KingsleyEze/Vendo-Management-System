package org.ng.undp.vdms.constants;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/16/2017.
 */
public enum NgoDocumentType {

    SUBSIDIARIES("SUBSIDIARIES"),
    QUALITY_ASSURANCE_CERTIFICATION("QUALITY ASSURANCE CERTIFICATION"),
    TECHNICAL_DOCUMENT("TECHNICAL DOCUMENT"),
    ENVIRONMENTAL_POLICY("ENVIRONMENTAL POLICY"),
    FINANCIAL_REPORT("FINANCIAL REPORT");

    private final String value;

    NgoDocumentType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }

    public static NgoDocumentType getEnum(String status){

        NgoDocumentType ngoDocumentType = null;
        switch (status){
            case "QUALITY ASSURANCE CERTIFICATION":
                ngoDocumentType = QUALITY_ASSURANCE_CERTIFICATION;
                break;
            case "TECHNICAL DOCUMENT":
                ngoDocumentType = TECHNICAL_DOCUMENT;
                break;
            case "ENVIRONMENTAL POLICY":
                ngoDocumentType = ENVIRONMENTAL_POLICY;
                break;
            case "FINANCIAL REPORT":
                ngoDocumentType = FINANCIAL_REPORT ;
                break;
        }

        return ngoDocumentType;
    }

}
