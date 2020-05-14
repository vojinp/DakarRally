package com.htec.vojinpesalj.dakarrally.repository.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehicles")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "vehicle_type", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor
@AllArgsConstructor
public abstract class Vehicle {
    @Id @GeneratedValue private Long id;

    @NotBlank private String teamName;

    @NotBlank private String model;

    @NotNull private Date manufacturingDate;

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties("vehicles")
    @JoinColumn(name = "race_id")
    private Race race;

    @OneToOne(cascade = {CascadeType.ALL})
    private VehicleStatistic vehicleStatistic;

    public Vehicle(
            String teamName,
            String model,
            Date manufacturingDate,
            VehicleStatistic vehicleStatistic) {
        this.teamName = teamName;
        this.model = model;
        this.manufacturingDate = manufacturingDate;
        this.vehicleStatistic = vehicleStatistic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(id, vehicle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public abstract Integer getMaxSpeed();

    public abstract Integer getRepairmentTime();

    public abstract Double getProbabilityOfLightMalfunction();

    public abstract Double getProbabilityOfHeavyMalfunction();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(Date manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public VehicleStatistic getVehicleStatistic() {
        return vehicleStatistic;
    }

    public void setVehicleStatistic(VehicleStatistic vehicleStatistic) {
        this.vehicleStatistic = vehicleStatistic;
    }
}
