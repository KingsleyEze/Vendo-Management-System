package org.ng.undp.vdms.constants;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/16/2017.
 */
public enum SupplierDocumentType {

    SUBSIDIARIES("SUBSIDIARIES"),
    QUALITY_ASSURANCE_CERTIFICATION("QUALITY ASSURANCE CERTIFICATION"),
    TECHNICAL_DOCUMENT("TECHNICAL DOCUMENT"),
    ENVIRONMENTAL_POLICY("ENVIRONMENTAL POLICY"),
    FINANCIAL_REPORT("FINANCIAL REPORT");

    private final String value;

    SupplierDocumentType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }

    public static SupplierDocumentType getEnum(String status){

        SupplierDocumentType supplierDocumentType = null;
        switch (status){
            case "QUALITY ASSURANCE CERTIFICATION":
                supplierDocumentType = QUALITY_ASSURANCE_CERTIFICATION;
                break;
            case "TECHNICAL DOCUMENT":
                supplierDocumentType = TECHNICAL_DOCUMENT;
                break;
            case "ENVIRONMENTAL POLICY":
                supplierDocumentType = ENVIRONMENTAL_POLICY ;
                break;
            case "FINANCIAL REPORT":
                supplierDocumentType = FINANCIAL_REPORT ;
                break;
        }

        return supplierDocumentType;
    }

}
