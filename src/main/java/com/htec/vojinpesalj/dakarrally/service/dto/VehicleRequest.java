package com.htec.vojinpesalj.dakarrally.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class VehicleRequest {
    @NotBlank(message = "VehicleRequest.teamName cannot be null or empty.")
    private String teamName;

    @NotBlank(message = "VehicleRequest.model cannot be null or empty.")
    private String model;

    @NotNull(message = "VehicleRequest.manufacturingDate cannot be null.")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date manufacturingDate;

    @NotNull(message = "VehicleRequest.type cannot be null.")
    private VehicleTypeDto type;
}
