package com.htec.vojinpesalj.dakarrally.repository.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "vehicle_statistics")
@Data
@Builder
@AllArgsConstructor
public class VehicleStatistic {
    @Id @GeneratedValue private Long id;
    private Double distance;
    private Double finishTime;
    private VehicleStatus status;

    public VehicleStatistic() {
        distance = 0.0;
        status = VehicleStatus.WORKING;
    }
}
