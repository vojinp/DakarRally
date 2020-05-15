package com.htec.vojinpesalj.dakarrally.service.simulator;

import com.htec.vojinpesalj.dakarrally.repository.Constants.ThreadInfo;
import com.htec.vojinpesalj.dakarrally.repository.domain.Race;
import com.htec.vojinpesalj.dakarrally.repository.domain.Vehicle;
import com.htec.vojinpesalj.dakarrally.service.RaceService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("prototype")
@Log4j2
public class RaceSimulatorThread extends Thread {
    private RaceService raceService;
    private Map<Long, VehicleSimulatorThread> vehicleSimulators;
    private List<Future> threadDoneCheck;
    private Long raceId;
    private ThreadPoolTaskExecutor taskExecutor;
    private ApplicationContext applicationContext;

    @Autowired
    public RaceSimulatorThread(
            RaceService raceService,
            ThreadPoolTaskExecutor taskExecutor,
            ApplicationContext applicationContext) {
        this.raceService = raceService;
        this.taskExecutor = taskExecutor;
        this.applicationContext = applicationContext;
        threadDoneCheck = new ArrayList<>();
        setDaemon(true);
    }

    public void setRace(Race race) {
        log.info(String.format("Initialized thread with raceId: %d.", race.getId()));
        this.vehicleSimulators =
                race.getVehicles().stream()
                        .map(this::mapAndStartThread)
                        .collect(Collectors.toMap(v -> v.getVehicle().getId(), v -> v));

        this.raceId = race.getId();
    }

    public void run() {
        log.info(String.format("Started thread with raceId: %d.", raceId));
        try {
            Thread.sleep(ThreadInfo.SLEEP_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
        while (!allVehiclesFinished()) {
            try {
                Thread.sleep(ThreadInfo.SLEEP_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        raceService.finishRace(raceId);
        log.info(String.format("Race with id: %d has finished.", raceId));
    }

    private Boolean allVehiclesFinished() {
        return threadDoneCheck.stream()
                .map(Future::isDone)
                .allMatch(isDone -> isDone == Boolean.TRUE);
    }

    public void removeVehicleFromRace(Vehicle vehicle) {
        vehicleSimulators.get(vehicle.getId()).removeFromRace();
        vehicleSimulators.remove(vehicle.getId());
    }

    private VehicleSimulatorThread mapAndStartThread(Vehicle vehicle) {
        VehicleSimulatorThread vehicleSimulatorThread =
                applicationContext.getBean(VehicleSimulatorThread.class);
        vehicleSimulatorThread.setVehicle(vehicle);
        threadDoneCheck.add(taskExecutor.submit(vehicleSimulatorThread));

        return vehicleSimulatorThread;
    }
}
