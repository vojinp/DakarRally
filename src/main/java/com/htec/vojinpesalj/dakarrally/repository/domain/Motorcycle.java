package com.htec.vojinpesalj.dakarrally.repository.domain;

import com.htec.vojinpesalj.dakarrally.repository.Constants.VehicleTypes;
import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue(value = VehicleTypes.MOTORCYCLE)
public abstract class Motorcycle extends Vehicle {
    public Motorcycle(String teamName, String model, Date manufacturingDate) {
        super(teamName, model, manufacturingDate);
    }
}
