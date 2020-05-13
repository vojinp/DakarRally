package com.htec.vojinpesalj.dakarrally.service.mappers;

import com.htec.vojinpesalj.dakarrally.repository.domain.Vehicle;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleResponse;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleTypeDto;
import org.springframework.stereotype.Service;

@Service
public class VehicleMapperImpl implements VehicleMapper {

    @Override
    public VehicleResponse toDto(Vehicle vehicle) {
        return VehicleResponse.builder()
                .id(vehicle.getId())
                .model(vehicle.getModel())
                .manufacturingDate(vehicle.getManufacturingDate())
                .teamName(vehicle.getTeamName())
                .type(VehicleTypeDto.fromString(vehicle.getClass().getSimpleName()))
                .build();
    }
}
