package com.htec.vojinpesalj.dakarrally.service.mappers;

import com.htec.vojinpesalj.dakarrally.repository.domain.VehicleStatistic;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleStatisticResponse;

public interface VehicleStatisticMapper {
    VehicleStatisticResponse toDto(VehicleStatistic vehicleStatistic);
}
