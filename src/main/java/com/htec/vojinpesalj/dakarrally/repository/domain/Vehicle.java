package com.htec.vojinpesalj.dakarrally.repository.domain;

import java.util.Date;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehicles")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "vehicle_type", discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Vehicle {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank private String teamName;

    @NotBlank private String model;

    @NotNull private Date manufacturingDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "race_id")
    private Race race;

    public Vehicle(String teamName, String model, Date manufacturingDate) {
        this.teamName = teamName;
        this.model = model;
        this.manufacturingDate = manufacturingDate;
    }

    public abstract Integer getMaxSpeed();

    public abstract Integer getRepairmentTime();

    public abstract Double getProbabilityOfLightMalfunction();

    public abstract Double getProbabilityOfHeavyMalfunction();
}
