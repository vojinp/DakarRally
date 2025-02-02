package com.htec.vojinpesalj.dakarrally.service.simulator;

import com.htec.vojinpesalj.dakarrally.repository.Constants;
import com.htec.vojinpesalj.dakarrally.repository.Constants.ThreadInfo;
import com.htec.vojinpesalj.dakarrally.repository.VehicleRepository;
import com.htec.vojinpesalj.dakarrally.repository.domain.Vehicle;
import com.htec.vojinpesalj.dakarrally.repository.domain.VehicleStatistic;
import com.htec.vojinpesalj.dakarrally.repository.domain.VehicleStatus;
import java.util.Date;
import java.util.Random;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Component
@Scope("prototype")
@Log4j2
public class VehicleSimulatorThread extends Thread {
    private final VehicleRepository vehicleRepository;
    private final Random random;
    private Vehicle vehicle;
    private Boolean vehicleRemovedFromRace;

    @Autowired
    public VehicleSimulatorThread(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
        this.random = new Random();
        this.vehicleRemovedFromRace = false;
        setDaemon(true);
    }

    public void setVehicle(Vehicle vehicle) {
        log.info(String.format("Initialized thread with vehicleId: %d.", vehicle.getId()));

        this.vehicle = vehicle;
    }

    private Boolean isFinished() {
        return vehicle.getVehicleStatistic().getDistance() > Constants.RACE_DISTANCE;
    }

    private Boolean isHeavyMalfunctioned() {
        return vehicle.getVehicleStatistic().getStatus() == VehicleStatus.HEAVY_MALFUNCTIONED;
    }

    public void run() {
        log.info(String.format("Started thread with vehicleId: %d.", vehicle.getId()));

        while (!isFinished() && !isHeavyMalfunctioned() && !vehicleRemovedFromRace) {
            VehicleStatistic vehicleStatistic = vehicle.getVehicleStatistic();

            // Calculating the distance that vehicle traveled every second
            vehicleStatistic.setDistance(
                    vehicleStatistic.getDistance()
                            + (double) vehicle.getMaxSpeed()
                                    / (ThreadInfo.HOUR_IN_MS / ThreadInfo.SLEEP_TIME));

            // Calculating possibility of light malfunction every second
            double lightMalfunctionProbability =
                    vehicle.getProbabilityOfLightMalfunction()
                            / ((double) ThreadInfo.HOUR_IN_MS / ThreadInfo.SLEEP_TIME);

            if (random.nextDouble() <= lightMalfunctionProbability) {
                log.info(
                        String.format(
                                "Vehicle with id: %d has light malfunction.", vehicle.getId()));
                vehicleStatistic.setStatus(VehicleStatus.LIGHT_MALFUNCTIONED);
                vehicleStatistic.getLightMalfunctions().add(new Date());
                vehicleRepository.save(vehicle);
            }

            // Calculating possibility of heavy malfunction every second
            double heavyMalfunctionProbability =
                    vehicle.getProbabilityOfHeavyMalfunction()
                            / ((double) ThreadInfo.HOUR_IN_MS / ThreadInfo.SLEEP_TIME);

            if (random.nextDouble() <= heavyMalfunctionProbability) {
                log.info(
                        String.format(
                                "Vehicle with id: %d has heavy malfunction.", vehicle.getId()));
                vehicleStatistic.setStatus(VehicleStatus.HEAVY_MALFUNCTIONED);
                vehicleRepository.save(vehicle);
            }

            if (vehicleStatistic.getStatus() == VehicleStatus.LIGHT_MALFUNCTIONED) {
                try {
                    Thread.sleep(vehicle.getRepairmentTime() * ThreadInfo.HOUR_IN_MS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }

            try {
                Thread.sleep(ThreadInfo.SLEEP_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        if (isFinished()) {
            vehicle.getVehicleStatistic().setDistance(Double.valueOf(Constants.RACE_DISTANCE));
            vehicle.getVehicleStatistic().setFinishTime(new Date());
            vehicleRepository.save(vehicle);
        }
        log.info(String.format("Vehicle with id: %d has finished the race.", vehicle.getId()));
    }

    public void removeFromRace() {
        log.info(
                String.format(
                        "Vehicle with id: %d has been removed from the race.", vehicle.getId()));

        this.vehicleRemovedFromRace = true;
    }
}
