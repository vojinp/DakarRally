package com.htec.vojinpesalj.dakarrally.service.simulator;

import com.htec.vojinpesalj.dakarrally.repository.Constants;
import com.htec.vojinpesalj.dakarrally.repository.Constants.VehicleThreadInfo;
import com.htec.vojinpesalj.dakarrally.repository.VehicleRepository;
import com.htec.vojinpesalj.dakarrally.repository.domain.Vehicle;
import com.htec.vojinpesalj.dakarrally.repository.domain.VehicleStatistic;
import com.htec.vojinpesalj.dakarrally.repository.domain.VehicleStatus;
import java.util.Date;
import java.util.Random;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class VehicleSimulatorThread extends Thread {
    @Autowired
    private VehicleRepository vehicleRepository;
    private Vehicle vehicle;
    private Boolean vehicleRemovedFromRace;
    private Random random;

    public VehicleSimulatorThread(Vehicle vehicle) {
        this.random = new Random();
        this.vehicleRemovedFromRace = false;
        this.vehicle = vehicle;
        setDaemon(true);
    }

    private Boolean isFinished() {
        return vehicle.getVehicleStatistic().getDistance() > Constants.RACE_DISTANCE;
    }

    private Boolean isHeavyMalfunctioned() {
        return vehicle.getVehicleStatistic().getStatus() == VehicleStatus.HEAVY_MALFUNCTIONED;
    }

    public void run() {
        while (!isFinished() && !isHeavyMalfunctioned() && !vehicleRemovedFromRace) {
            VehicleStatistic vehicleStatistic = vehicle.getVehicleStatistic();

            // Calculating the distance that vehicle traveled every second
            vehicleStatistic.setDistance(
                    vehicleStatistic.getDistance()
                            + vehicle.getMaxSpeed()
                                    / (VehicleThreadInfo.HOUR_IN_MS
                                            / VehicleThreadInfo.SLEEP_TIME));

            // Calculating possibility of light malfunction every second
            double lightMalfunctionProbability =
                    vehicle.getProbabilityOfLightMalfunction()
                            / ((double) VehicleThreadInfo.HOUR_IN_MS
                                    / VehicleThreadInfo.SLEEP_TIME);

            if (random.nextDouble() <= lightMalfunctionProbability) {
                vehicleStatistic.setStatus(VehicleStatus.LIGHT_MALFUNCTIONED);
                vehicleStatistic.getLightMalfunctions().add(new Date());
                vehicleRepository.save(vehicle);
            }

            // Calculating possibility of heavy malfunction every second
            double heavyMalfunctionProbability =
                    vehicle.getProbabilityOfHeavyMalfunction()
                            / ((double) VehicleThreadInfo.HOUR_IN_MS
                                    / VehicleThreadInfo.SLEEP_TIME);

            if (random.nextDouble() <= heavyMalfunctionProbability) {
                vehicleStatistic.setStatus(VehicleStatus.HEAVY_MALFUNCTIONED);
                vehicleRepository.save(vehicle);
            }

            if (vehicleStatistic.getStatus() == VehicleStatus.LIGHT_MALFUNCTIONED) {
                try {
                    Thread.sleep(vehicle.getRepairmentTime() * VehicleThreadInfo.HOUR_IN_MS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }

            try {
                Thread.sleep(VehicleThreadInfo.SLEEP_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        if (isFinished()) {
            vehicle.getVehicleStatistic().setFinishTime(new Date());
            vehicleRepository.save(vehicle);
        }
        System.out.println("VEHICLE FINISHED!");
    }

    public void removeFromRace() {
        this.vehicleRemovedFromRace = true;
    }
}
