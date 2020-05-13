package com.htec.vojinpesalj.dakarrally.service;

import com.htec.vojinpesalj.dakarrally.repository.domain.Vehicle;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleRequest;

public interface VehicleFactory {
    Vehicle createVehicle(VehicleRequest vehicleRequest);
}
