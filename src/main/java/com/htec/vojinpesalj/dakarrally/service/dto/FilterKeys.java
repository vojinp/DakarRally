package com.htec.vojinpesalj.dakarrally.service.dto;

public enum FilterKeys {
    TEAM("teamName"),
    MODEL("model"),
    MANUFACTURING_DATE("manufacturingDate"),
    STATUS("vehicleStatistic::status");

    private String value;

    FilterKeys(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
