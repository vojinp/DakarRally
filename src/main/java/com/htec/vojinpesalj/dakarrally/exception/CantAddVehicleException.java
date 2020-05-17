package com.htec.vojinpesalj.dakarrally.exception;

import com.htec.vojinpesalj.dakarrally.repository.Constants.ErrorMessages;

public class CantAddVehicleException extends RuntimeException {

    public CantAddVehicleException(Long raceId) {
        super(String.format(ErrorMessages.CANT_ADD_VEHICLE_TO_RACE, raceId));
    }
}
