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
@DiscriminatorValue(value = VehicleTypes.CROSS_MOTORCYCLE)
public class CrossMotorcycle extends Motorcycle {
    @Builder
    public CrossMotorcycle(
            String teamName,
            String model,
            Date manufacturingDate,
            VehicleStatistic vehicleStatistic) {
        super(teamName, model, manufacturingDate, vehicleStatistic);
    }

    @Override
    public Integer getMaxSpeed() {
        return MaxSpeeds.CROSS_MOTORCYCLE_MAX_SPEED;
    }

    @Override
    public Integer getRepairmentTime() {
        return MalfunctionRepairmentsTime.CAR_REPAIRMENT_TIME;
    }

    @Override
    public Double getProbabilityOfLightMalfunction() {
        return ProbabilityOfMalfunctions.CROSS_MOTORCYCLE_LIGHT_MALFUNCTION;
    }

    @Override
    public Double getProbabilityOfHeavyMalfunction() {
        return ProbabilityOfMalfunctions.CROSS_MOTORCYCLE_HEAVY_MALFUNCTION;
    }
}
