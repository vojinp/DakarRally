package com.htec.vojinpesalj.dakarrally.service.mappers;

import com.htec.vojinpesalj.dakarrally.repository.domain.Vehicle;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleResponse;

public interface VehicleMapper {
    VehicleResponse toDto(Vehicle vehicle);
}
