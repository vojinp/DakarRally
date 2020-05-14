package com.htec.vojinpesalj.dakarrally.service.simulator;

import com.htec.vojinpesalj.dakarrally.repository.Constants.VehicleThreadInfo;
import com.htec.vojinpesalj.dakarrally.repository.domain.Race;
import com.htec.vojinpesalj.dakarrally.repository.domain.Vehicle;
import com.htec.vojinpesalj.dakarrally.service.RaceService;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class RaceSimulatorThread extends Thread {
    @Autowired private RaceService raceService;
    private Map<Long, VehicleSimulatorThread> vehicleSimulators;
    private Long raceId;

    public RaceSimulatorThread(Race race) {
        this.vehicleSimulators =
                race.getVehicles().stream()
                        .map(VehicleSimulatorThread::new)
                        .collect(Collectors.toMap(v -> v.getVehicle().getId(), v -> v));
        this.raceId = race.getId();
        setDaemon(true);
    }

    public void run() {
        vehicleSimulators.values().forEach(VehicleSimulatorThread::start);
        while (!allVehiclesFinished()) {
            try {
                Thread.sleep(VehicleThreadInfo.SLEEP_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        raceService.finishRace(raceId);
        System.out.println("RACE IS DONE!");
    }

    private Boolean allVehiclesFinished() {
        return vehicleSimulators.values().stream()
                .map(VehicleSimulatorThread::isAlive)
                .allMatch(isAlive -> isAlive == Boolean.FALSE);
    }

    public void removeVehicleFromRace(Vehicle vehicle) {
        vehicleSimulators.get(vehicle.getId()).removeFromRace();
        vehicleSimulators.remove(vehicle.getId());
    }
}
