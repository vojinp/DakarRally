package com.htec.vojinpesalj.dakarrally.service.simulator;

import com.htec.vojinpesalj.dakarrally.repository.Constants.ThreadInfo;
import com.htec.vojinpesalj.dakarrally.repository.domain.Race;
import com.htec.vojinpesalj.dakarrally.repository.domain.Vehicle;
import com.htec.vojinpesalj.dakarrally.service.RaceService;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("prototype")
public class RaceSimulatorThread extends Thread {
    private RaceService raceService;
    private Map<Long, VehicleSimulatorThread> vehicleSimulators;
    private Long raceId;
    private TaskExecutor taskExecutor;
    private ApplicationContext applicationContext;

    @Autowired
    public RaceSimulatorThread(
            RaceService raceService,
            TaskExecutor taskExecutor,
            ApplicationContext applicationContext) {
        this.raceService = raceService;
        this.taskExecutor = taskExecutor;
        this.applicationContext = applicationContext;
        setDaemon(true);
    }

    public void setRace(Race race) {
        this.vehicleSimulators =
                race.getVehicles().stream()
                        .map(this::mapAndStartThread)
                        .collect(Collectors.toMap(v -> v.getVehicle().getId(), v -> v));

        this.raceId = race.getId();
    }

    public void run() {
        vehicleSimulators.values().forEach(VehicleSimulatorThread::start);
        while (!allVehiclesFinished()) {
            try {
                Thread.sleep(ThreadInfo.SLEEP_TIME);
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

    private VehicleSimulatorThread mapAndStartThread(Vehicle vehicle) {
        VehicleSimulatorThread vehicleSimulatorThread =
                applicationContext.getBean(VehicleSimulatorThread.class);
        vehicleSimulatorThread.setVehicle(vehicle);
        taskExecutor.execute(vehicleSimulatorThread);

        return vehicleSimulatorThread;
    }
}
