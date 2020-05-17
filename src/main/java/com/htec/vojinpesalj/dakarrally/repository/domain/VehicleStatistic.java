package com.htec.vojinpesalj.dakarrally.repository.domain;

import java.util.Collection;
import java.util.Date;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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

    @NotNull private Double distance;

    private Date finishTime;

    @Enumerated(EnumType.STRING)
    private VehicleStatus status;

    @ElementCollection private Collection<Date> lightMalfunctions;

    public VehicleStatistic() {
        distance = 0.0;
        status = VehicleStatus.WORKING;
    }
}
