package com.htec.vojinpesalj.dakarrally.service.dto;

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
public class VehicleResponse {
    @NotBlank private Long id;

    @NotBlank private String teamName;

    @NotBlank private String model;

    @NotNull private Date manufacturingDate;

    @NotNull private VehicleTypeDto type;
}
