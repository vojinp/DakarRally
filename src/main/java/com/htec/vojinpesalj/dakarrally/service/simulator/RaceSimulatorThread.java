package com.htec.vojinpesalj.dakarrally.service.simulator;

import com.htec.vojinpesalj.dakarrally.repository.domain.Race;
import com.htec.vojinpesalj.dakarrally.repository.domain.Vehicle;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class RaceSimulatorThread extends Thread {
    private List<VehicleSimulatorThread> vehicleSimulators;

    public RaceSimulatorThread(Race race) {
        this.vehicleSimulators =
                race.getVehicles().stream()
                        .map(VehicleSimulatorThread::new)
                        .collect(Collectors.toList());
        setDaemon(true);
    }

    public void run() {
        vehicleSimulators.forEach(VehicleSimulatorThread::start);
    }

    public void removeVehicleFromRace(Vehicle vehicle) {
        vehicleSimulators.stream()
                .filter(vehicleSimulator -> vehicleSimulator.getVehicle().equals(vehicle))
                .findFirst()
                .ifPresent(VehicleSimulatorThread::removeFromRace);

        vehicleSimulators =
                vehicleSimulators.stream()
                        .filter(vehicleSimulator -> !vehicleSimulator.getVehicle().equals(vehicle))
                        .collect(Collectors.toList());
    }
}
