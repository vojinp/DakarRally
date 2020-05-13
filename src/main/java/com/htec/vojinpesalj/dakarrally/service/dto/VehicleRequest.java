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
    @NotBlank private String teamName;

    @NotBlank private String model;

    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date manufacturingDate;

    @NotNull private VehicleTypeDto type;
}
