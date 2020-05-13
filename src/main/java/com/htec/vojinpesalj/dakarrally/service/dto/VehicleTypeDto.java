package com.htec.vojinpesalj.dakarrally.service.dto;

import com.htec.vojinpesalj.dakarrally.repository.Constants.ErrorMessages;

public enum VehicleTypeDto {
    SPORTS_CAR("SportsCar"),
    TERRAIN_CAR("TerrainCar"),
    TRUCK("Truck"),
    CROSS_MOTORCYCLE("CrossMotorcycle"),
    SPORT_MOTORCYCLE("SportMotorcycle");

    private String text;

    VehicleTypeDto(String text) {
        this.text = text;
    }

    public static VehicleTypeDto fromString(String text) {
        for (VehicleTypeDto b : VehicleTypeDto.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new UnsupportedOperationException(ErrorMessages.VEHICLE_TYPE_NOT_SUPPORTED);
    }
}
