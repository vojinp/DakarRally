package com.htec.vojinpesalj.dakarrally.util;

import java.util.Date;

public final class Constants {
    public static class Urls {
        public static final String CREATE_RACE = "/api/races";
        public static final String START_RACE = "/api/races/%s";
        public static final String AUTHORIZE = "/api/auth/authenticate";
        public static final String GET_RACE_STATISTIC = "/api/races/%s/statistic";
        public static final String UPDATE_VEHICLE = "/api/vehicles/%s";
        public static final String DELETE_VEHICLE = "/api/vehicles/%s";
        public static final String FIND_VEHICLE = "/api/vehicles/filter";
        public static final String GET_VEHICLE_STATISTIC = "/api/vehicles/%s/statistic";
        public static final String CREATE_VEHICLE = "/api/races/%s/vehicles";
        public static final String GET_LEADERBOARD = "/api/races/%s/vehicles/leaderboard";
        public static final String REGISTER = "/api/auth/signup";
    }

    public static class QueryKeys {
        public static final String YEAR = "year";
        public static final String TYPE = "type";
    }

    public static class QueryValues {
        public static final String YEAR = "2000";
        public static final String SPORTS_CAR = "SPORTS_CAR";
    }

    public static class HeaderKeys {
        public static final String AUTHORIZATION = "Authorization";
    }

    public static class HeaderValues {
        public static final String BEARER = "Bearer";
    }

    public static class FormValues {
        public static final String ADMIN_USERNAME = "admin";
        public static final String ADMIN_PASSWORD = "admin";
        public static final String USER_USERNAME = "user";
        public static final String USER_PASSWORD = "user";
        public static final String NEW_TEAM_NAME = "new team name";
        public static final String NEW_MODEL = "new team name";
        public static final Date NEW_MANUFACTURING_DATE = new Date();
        public static final String INVALID_USERNAME = "invalid_username";
        public static final String NEW_NAME = "new_user";
        public static final String NEW_USERNAME = "new_user";
        public static final String NEW_EMAIL = "new_user@gmail.com";
        public static final String NEW_PASSWORD = "new_user";
    }

    public static class DatabaseIds {
        public static final String RACE_ID = "4";
        public static final String RUNNING_RACE_ID = "5";
        public static final String FINISHED_RACE_ID = "6";
        public static final String VEHICLE_ID = "8";
    }

    public static class ResponseValues {
        public static final String PENDING = "PENDING";
        public static final String RUNNING = "RUNNING";
        public static final String WORKING = "WORKING";
    }
}
