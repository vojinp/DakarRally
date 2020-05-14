package com.htec.vojinpesalj.dakarrally.repository.domain;

import com.htec.vojinpesalj.dakarrally.repository.Constants.VehicleTypes;
import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue(value = VehicleTypes.CAR)
public abstract class Car extends Vehicle {
    public Car(
            String teamName,
            String model,
            Date manufacturingDate,
            VehicleStatistic vehicleStatistic) {
        super(teamName, model, manufacturingDate, vehicleStatistic);
    }
}
