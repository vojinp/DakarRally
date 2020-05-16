package com.htec.vojinpesalj.dakarrally.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Statistic for the race")
public class RaceStatisticResponse {
    @NotNull
    @ApiModelProperty(notes = "Status of the race")
    private RaceStatusDto status;

    @NotNull
    @ApiModelProperty(notes = "Number of vehicles grouped by status")
    private Map<VehicleStatusDto, Integer> numberOfVehiclesByStatus;

    @NotNull
    @ApiModelProperty(notes = "Number of vehicles grouped by type")
    private Map<VehicleTypeDto, Integer> numberOfVehiclesByType;
}
