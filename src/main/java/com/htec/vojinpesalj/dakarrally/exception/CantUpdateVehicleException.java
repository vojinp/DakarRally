package com.htec.vojinpesalj.dakarrally.exception;

import com.htec.vojinpesalj.dakarrally.repository.Constants.ErrorMessages;

public class CantUpdateVehicleException extends RuntimeException {

    public CantUpdateVehicleException(Long id) {
        super(String.format(ErrorMessages.CANT_UPDATE_VEHICLE, id));
    }
}
