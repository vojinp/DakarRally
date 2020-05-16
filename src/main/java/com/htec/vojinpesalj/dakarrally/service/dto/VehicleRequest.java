package com.htec.vojinpesalj.dakarrally.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class VehicleRequest {
    @NotBlank(message = "VehicleRequest.teamName cannot be null or empty.")
    @ApiModelProperty(notes = "Name of the vehicle's team")
    private String teamName;

    @NotBlank(message = "VehicleRequest.model cannot be null or empty.")
    @ApiModelProperty(notes = "Model of the vehicle")
    private String model;

    @NotNull(message = "VehicleRequest.manufacturingDate cannot be null.")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @ApiModelProperty(notes = "Date when the vehicle was manufactured")
    private Date manufacturingDate;

    @NotNull(message = "VehicleRequest.type cannot be null.")
    @ApiModelProperty(notes = "Type of the vehicle")
    private VehicleTypeDto type;
}
