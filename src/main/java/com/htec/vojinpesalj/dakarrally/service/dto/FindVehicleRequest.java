package com.htec.vojinpesalj.dakarrally.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Filter parameters for finding vehicle")
public class FindVehicleRequest {
    @NotNull(message = "FindVehicleRequest.filterKeys cannot be null.")
    @ApiModelProperty(notes = "Sorted keys for the filter operation")
    List<FilterKeys> filterKeys;

    @NotNull(message = "FindVehicleRequest.filterValues cannot be null.")
    @ApiModelProperty(notes = "Sorted values for the filter operation")
    List<String> filterValues;

    @NotNull(message = "FindVehicleRequest.filterOperations cannot be null.")
    @ApiModelProperty(notes = "Sorted AND/OR operations")
    List<FilterOperation> filterOperations;

    @NotNull(message = "FindVehicleRequest.sortBy cannot be null.")
    @ApiModelProperty(notes = "Key which will be used for sorting results")
    FilterKeys sortBy;
}
