package com.htec.vojinpesalj.dakarrally.repository.domain;

import com.htec.vojinpesalj.dakarrally.repository.Constants.MalfunctionRepairmentsTime;
import com.htec.vojinpesalj.dakarrally.repository.Constants.MaxSpeeds;
import com.htec.vojinpesalj.dakarrally.repository.Constants.ProbabilityOfMalfunctions;
import com.htec.vojinpesalj.dakarrally.repository.Constants.VehicleTypes;
import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue(value = VehicleTypes.TRUCK)
public class Truck extends Vehicle {
    @Builder
    public Truck(String teamName, String model, Date manufacturingDate) {
        super(teamName, model, manufacturingDate);
    }

    @Override
    public Integer getMaxSpeed() {
        return MaxSpeeds.TRUCK_MAX_SPEED;
    }

    @Override
    public Integer getRepairmentTime() {
        return MalfunctionRepairmentsTime.TRUCK_REPAIRMENT_TIME;
    }

    @Override
    public Double getProbabilityOfLightMalfunction() {
        return ProbabilityOfMalfunctions.TRUCK_LIGHT_MALFUNCTION;
    }

    @Override
    public Double getProbabilityOfHeavyMalfunction() {
        return ProbabilityOfMalfunctions.TRUCK_HEAVY_MALFUNCTION;
    }
}
