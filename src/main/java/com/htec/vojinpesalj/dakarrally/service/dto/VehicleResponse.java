package com.htec.vojinpesalj.dakarrally.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Details of the vehicle")
public class VehicleResponse {
    @NotBlank
    @ApiModelProperty(notes = "Id of the vehicle")
    private Long id;

    @NotBlank
    @ApiModelProperty(notes = "Name of the vehicle's team")
    private String teamName;

    @NotBlank
    @ApiModelProperty(notes = "Model of the vehicle")
    private String model;

    @NotNull
    @ApiModelProperty(notes = "Date when the vehicle was manufactured")
    private Date manufacturingDate;

    @NotNull
    @ApiModelProperty(notes = "Type of the vehicle")
    private VehicleTypeDto type;

    @NotNull
    @ApiModelProperty(notes = "Statistic of the vehicle")
    private VehicleStatisticResponse vehicleStatisticResponse;
}
