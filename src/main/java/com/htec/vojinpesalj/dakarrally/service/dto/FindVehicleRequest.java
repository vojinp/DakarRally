package com.htec.vojinpesalj.dakarrally.service.dto;

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
public class FindVehicleRequest {
    @NotNull(message = "FindVehicleRequest.filterKeys cannot be null.")
    List<FilterKeys> filterKeys;
    @NotNull(message = "FindVehicleRequest.filterValues cannot be null.")
    List<String> filterValues;
    @NotNull(message = "FindVehicleRequest.filterOperations cannot be null.")
    List<FilterOperation> filterOperations;
    @NotNull(message = "FindVehicleRequest.sortBy cannot be null.")
    FilterKeys sortBy;
}
