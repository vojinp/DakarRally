package com.htec.vojinpesalj.dakarrally.service.mappers;

import com.htec.vojinpesalj.dakarrally.repository.domain.VehicleStatistic;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleStatisticResponse;
import org.springframework.stereotype.Service;

@Service
public class VehicleStatisticMapperImpl implements VehicleStatisticMapper {

    @Override
    public VehicleStatisticResponse toDto(VehicleStatistic vehicleStatistic) {
        return VehicleStatisticResponse.builder()
                .id(vehicleStatistic.getId())
                .distance(vehicleStatistic.getDistance())
                .finishTime(vehicleStatistic.getFinishTime())
                .status(vehicleStatistic.getStatus())
                .lightMalfunctions(vehicleStatistic.getLightMalfunctions())
                .build();
    }
}
