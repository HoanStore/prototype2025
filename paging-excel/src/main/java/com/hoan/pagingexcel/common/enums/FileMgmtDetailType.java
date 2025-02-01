package com.hoan.pagingexcel.common.enums;

public enum FileMgmtDetailType {

    COMMON_FILE("F001"),
    COMMON_IMAGE("I001"),
    BROCHURE_KO("K302"),
    BROCHURE_EN("E302"),
    BROCHURE_CN("C302"),
    BROCHURE_ES("S302"),
    BROCHURE_COMMON("I302"),
    ORGANIZATION_IMAGE("O105"),
    FLAG_IMAGE("N104"),
    THUMBNAIL_IMAGE("T205"), // 갤러리 썸네일,
    ONLINE_THUMBNAIL_IMAGE("T401"), // 온라인 강의 썸네일
    COMPANY_LOGO_IMAGE("I701"),
    PRODUCT_TECH_IMAGE("P701"),
    ;



    private final String value;

    FileMgmtDetailType(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

}
