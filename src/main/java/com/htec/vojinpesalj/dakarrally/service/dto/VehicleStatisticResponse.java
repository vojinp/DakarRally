package com.htec.vojinpesalj.dakarrally.service.dto;

import com.htec.vojinpesalj.dakarrally.repository.domain.VehicleStatus;
import java.util.Collection;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleStatisticResponse {
    private Long id;
    private Double distance;
    private Date finishTime;
    private Collection<Date> lightMalfunctions;
    private VehicleStatusDto status;
}
