package com.htec.vojinpesalj.dakarrally.service.mappers;

import com.htec.vojinpesalj.dakarrally.repository.Constants.ErrorMessages;
import com.htec.vojinpesalj.dakarrally.repository.domain.VehicleStatus;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleStatusDto;
import org.springframework.stereotype.Service;

@Service
public class VehicleStatusMapperImpl implements VehicleStatusMapper {

    @Override
    public VehicleStatusDto toDto(VehicleStatus vehicleStatus) {
        switch (vehicleStatus) {
            case WORKING:
                return VehicleStatusDto.WORKING;
            case LIGHT_MALFUNCTIONED:
                return VehicleStatusDto.LIGHT_MALFUNCTIONED;
            case HEAVY_MALFUNCTIONED:
                return VehicleStatusDto.HEAVY_MALFUNCTIONED;
            default:
                throw new UnsupportedOperationException(ErrorMessages.RACE_STATUS_NOT_SUPPORTED);
        }
    }
}
