package com.htec.vojinpesalj.dakarrally.service.dto;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaceStatisticResponse {
    private RaceStatusDto status;
    private Map<VehicleStatusDto, Integer> numberOfVehiclesByStatus;
    private Map<VehicleTypeDto, Integer> numberOfVehiclesByType;
}
