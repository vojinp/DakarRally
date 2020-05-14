package com.htec.vojinpesalj.dakarrally.service.mappers;

import com.htec.vojinpesalj.dakarrally.repository.domain.Vehicle;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleResponse;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleMapperImpl implements VehicleMapper {
    private VehicleStatisticMapper vehicleStatisticMapper;

    @Autowired
    public VehicleMapperImpl(VehicleStatisticMapper vehicleStatisticMapper) {
        this.vehicleStatisticMapper = vehicleStatisticMapper;
    }

    @Override
    public VehicleResponse toDto(Vehicle vehicle) {
        return VehicleResponse.builder()
                .id(vehicle.getId())
                .model(vehicle.getModel())
                .manufacturingDate(vehicle.getManufacturingDate())
                .teamName(vehicle.getTeamName())
                .type(VehicleTypeDto.fromString(vehicle.getClass().getSimpleName()))
                .vehicleStatisticResponse(
                        vehicleStatisticMapper.toDto(vehicle.getVehicleStatistic()))
                .build();
    }
}
