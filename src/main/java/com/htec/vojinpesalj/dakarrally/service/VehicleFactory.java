package com.htec.vojinpesalj.dakarrally.service;

import com.htec.vojinpesalj.dakarrally.repository.domain.Vehicle;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleRequest;

public interface VehicleFactory {

    /**
     * Create new vehicle from the factory
     *
     * @param vehicleRequest fields that will vehicle contain
     * @return Vehicle with given fields and class
     */
    Vehicle createVehicle(VehicleRequest vehicleRequest);
}
