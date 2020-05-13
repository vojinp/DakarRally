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
@DiscriminatorValue(value = VehicleTypes.SPORT_MOTORCYCLE)
public class SportMotorcycle extends Motorcycle {
    @Builder
    public SportMotorcycle(String teamName, String model, Date manufacturingDate) {
        super(teamName, model, manufacturingDate);
    }

    @Override
    public Integer getMaxSpeed() {
        return MaxSpeeds.SPORT_MOTORCYCLE_MAX_SPEDD;
    }

    @Override
    public Integer getRepairmentTime() {
        return MalfunctionRepairmentsTime.MOTORCYCLE_REPAIRMENT_TIME;
    }

    @Override
    public Double getProbabilityOfLightMalfunction() {
        return ProbabilityOfMalfunctions.SPORT_MOTORCYCLE_LIGHT_MALFUNCTION;
    }

    @Override
    public Double getProbabilityOfHeavyMalfunction() {
        return ProbabilityOfMalfunctions.SPORT_MOTORCYCLE_HEAVY_MALFUNCTION;
    }
}
