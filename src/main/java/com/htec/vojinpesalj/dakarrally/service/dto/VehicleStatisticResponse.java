package com.htec.vojinpesalj.dakarrally.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Collection;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Statistic of the vehicle")
public class VehicleStatisticResponse {

    @NotNull
    @ApiModelProperty(notes = "If of the vehicle")
    private Long id;

    @ApiModelProperty(notes = "Distance that vehicle traveled in the race")
    private Double distance;

    @NotNull
    @ApiModelProperty(notes = "Date and time when vehicle finished the race")
    private Date finishTime;

    @NotNull
    @ApiModelProperty(notes = "Collection of dates when the vehicle had light malfunctions")
    private Collection<Date> lightMalfunctions;

    @NotNull
    @ApiModelProperty(notes = "Status of the vehicle")
    private VehicleStatusDto status;
}
