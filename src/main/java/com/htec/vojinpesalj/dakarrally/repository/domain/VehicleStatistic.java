package com.htec.vojinpesalj.dakarrally.repository.domain;

import java.util.Collection;
import java.util.Date;
import javax.persistence.ElementCollection;
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
    private Date finishTime;
    private VehicleStatus status;

    @ElementCollection private Collection<Date> lightMalfunctions;

    public VehicleStatistic() {
        distance = 0.0;
        status = VehicleStatus.WORKING;
    }
}
