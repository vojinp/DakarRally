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
@DiscriminatorValue(value = VehicleTypes.SPORTS_CAR)
public class SportsCar extends Car {

    @Builder
    public SportsCar(String teamName, String model, Date manufacturingDate) {
        super(teamName, model, manufacturingDate);
    }

    @Override
    public Integer getMaxSpeed() {
        return MaxSpeeds.SPORTS_CAR_MAX_SPEED;
    }

    @Override
    public Integer getRepairmentTime() {
        return MalfunctionRepairmentsTime.CAR_REPAIRMENT_TIME;
    }

    @Override
    public Double getProbabilityOfLightMalfunction() {
        return ProbabilityOfMalfunctions.SPORTS_CAR_LIGHT_MALFUNCTION;
    }

    @Override
    public Double getProbabilityOfHeavyMalfunction() {
        return ProbabilityOfMalfunctions.SPORTS_CAR_HEAVY_MALFUNCTION;
    }
}
