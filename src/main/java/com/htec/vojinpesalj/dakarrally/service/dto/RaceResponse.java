package com.htec.vojinpesalj.dakarrally.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Details of the race")
public class RaceResponse {
    @NotNull
    @ApiModelProperty(notes = "Id of the race")
    private Long id;

    @NotNull
    @ApiModelProperty(notes = "Year when the race is held")
    private Integer year;

    @NotNull
    @ApiModelProperty(notes = "Status of the race")
    private RaceStatusDto status;
}
