package com.htec.vojinpesalj.dakarrally.service.mappers;

import com.htec.vojinpesalj.dakarrally.repository.domain.VehicleStatus;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleStatusDto;

public interface VehicleStatusMapper {
    VehicleStatusDto toDto(VehicleStatus vehicleStatus);
}
