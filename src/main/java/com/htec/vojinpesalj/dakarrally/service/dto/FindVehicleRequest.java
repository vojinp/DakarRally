package com.htec.vojinpesalj.dakarrally.service.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindVehicleRequest {
    List<FilterKeys> filterKeys;
    List<String> filterValues;
    List<FilterOperation> filterOperations;
    FilterKeys sortBy;
}
