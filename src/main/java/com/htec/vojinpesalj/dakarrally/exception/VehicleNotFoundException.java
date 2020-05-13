package com.htec.vojinpesalj.dakarrally.exception;

public class VehicleNotFoundException extends EntityNotFoundException {

    public VehicleNotFoundException(Long id) {
        super("Vehicle", id);
    }
}
