package com.htec.vojinpesalj.dakarrally.service.mappers;

import com.htec.vojinpesalj.dakarrally.repository.domain.VehicleStatistic;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleStatisticResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleStatisticMapperImpl implements VehicleStatisticMapper {
    private VehicleStatusMapper vehicleStatusMapper;

    @Autowired
    public VehicleStatisticMapperImpl(VehicleStatusMapper vehicleStatusMapper) {
        this.vehicleStatusMapper = vehicleStatusMapper;
    }

    @Override
    public VehicleStatisticResponse toDto(VehicleStatistic vehicleStatistic) {
        return VehicleStatisticResponse.builder()
                .id(vehicleStatistic.getId())
                .distance(vehicleStatistic.getDistance())
                .finishTime(vehicleStatistic.getFinishTime())
                .status(vehicleStatusMapper.toDto(vehicleStatistic.getStatus()))
                .lightMalfunctions(vehicleStatistic.getLightMalfunctions())
                .build();
    }
}
