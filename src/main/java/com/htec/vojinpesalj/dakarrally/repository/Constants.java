package com.htec.vojinpesalj.dakarrally.repository;

public final class Constants {
    public static final Integer RACE_DISTANCE = 10000;

    public static class MaxSpeeds {
        public static final Integer SPORTS_CAR_MAX_SPEED = 140;
        public static final Integer TERRAIN_CAR_MAX_SPEED = 100;
        public static final Integer TRUCK_MAX_SPEED = 80;
        public static final Integer CROSS_MOTORCYCLE_MAX_SPEED = 85;
        public static final Integer SPORT_MOTORCYCLE_MAX_SPEED = 130;
    }

    public static class MalfunctionRepairmentsTime {
        public static final Integer CAR_REPAIRMENT_TIME = 5;
        public static final Integer TRUCK_REPAIRMENT_TIME = 7;
        public static final Integer MOTORCYCLE_REPAIRMENT_TIME = 3;
    }

    public static class ProbabilityOfMalfunctions {
        public static final Double SPORTS_CAR_LIGHT_MALFUNCTION = 0.12;
        public static final Double SPORTS_CAR_HEAVY_MALFUNCTION = 0.02;
        public static final Double TERRAIN_CAR_LIGHT_MALFUNCTION = 0.03;
        public static final Double TERRAIN_CAR_HEAVY_MALFUNCTION = 0.01;
        public static final Double TRUCK_LIGHT_MALFUNCTION = 0.06;
        public static final Double TRUCK_HEAVY_MALFUNCTION = 0.04;
        public static final Double CROSS_MOTORCYCLE_LIGHT_MALFUNCTION = 0.03;
        public static final Double CROSS_MOTORCYCLE_HEAVY_MALFUNCTION = 0.02;
        public static final Double SPORT_MOTORCYCLE_LIGHT_MALFUNCTION = 0.18;
        public static final Double SPORT_MOTORCYCLE_HEAVY_MALFUNCTION = 0.1;
    }

    public static class VehicleTypes {
        public static final String CAR = "CAR";
        public static final String MOTORCYCLE = "MOTORCYCLE";
        public static final String TRUCK = "TRUCK";
        public static final String SPORTS_CAR = "SPORTS_CAR";
        public static final String TERRAIN_CAR = "TERRAIN_CAR";
        public static final String CROSS_MOTORCYCLE = "CROSS_MOTORCYCLE";
        public static final String SPORT_MOTORCYCLE = "SPORT_MOTORCYCLE";
    }

    public static class ErrorMessages {
        public static final String VEHICLE_TYPE_NOT_SUPPORTED = "Vehicle type is not supported.";
        public static final String RACE_STATUS_NOT_SUPPORTED = "Race status is not supported.";
        public static final String ENTITY_WITH_ID_NOT_FOUND = "%s with id: %d was not found.";
        public static final String CANT_UPDATE_VEHICLE =
                "Can't update vehicle with id: %d while race is running.";
        public static final String RACE_ALREADY_STARTED = "Race with id: %d already started.";
    }

    public static class ThreadInfo {
        public static final Integer SLEEP_TIME = 1000;
        public static final Integer HOUR_IN_MS = 3600000;
        public static final Integer CORE_POOL_SIZE = 10;
        public static final Integer MAX_POOL_SIZE = 10;
    }
}
