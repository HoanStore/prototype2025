package com.hoan.pagingexcel.common.enums;

public enum MgmtTypes {

    EXAMPLES("101"),
    WORLD_ITS_MARKET_INFO("102"),
    ITS_ORDER("103"),
    WORLD_COMPANY("104"),
    WORLD_COMPANY_EN_ES("701"),
    WORLD_ORGANIZATION_INFO("105"),
    TECH_MARKET_TREND("106"),

    ROAD_SHOW("201"),
    TRAINING("202"),
    APPLICATION("203"),
    GALLERY("205"),
    SCHEDULE("206"),

    VIDEO("301"),
    BROCHURE("302"),
    NEWSLETTER("303"),

    ONLINE_LEC("401"),
    LIBRARY("402"),

    NOTICE("501"),

    POPUP("601"),
    BANNER("602"),
    PARTICIPANT("603"),
    COLLABORATE("604"),
    BIZ("605"),
    NCARD("606"),

    COMPANIES("701"),
    MATCHING("702");

    private final String value;

    MgmtTypes(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

}
