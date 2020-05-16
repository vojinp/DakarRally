package com.htec.vojinpesalj.dakarrally.service.simulator;

import com.htec.vojinpesalj.dakarrally.exception.RaceNotFoundException;
import com.htec.vojinpesalj.dakarrally.repository.domain.Race;
import com.htec.vojinpesalj.dakarrally.repository.domain.Vehicle;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Data
@Service
public class RaceSimulationServiceImpl implements RaceSimulationService {
    private Map<Long, RaceSimulatorThread> raceSimulations;

    private TaskExecutor taskExecutor;
    private ApplicationContext applicationContext;

    @Autowired
    public RaceSimulationServiceImpl(
            TaskExecutor taskExecutor, ApplicationContext applicationContext) {
        this.taskExecutor = taskExecutor;
        this.applicationContext = applicationContext;
        this.raceSimulations = new HashMap<>();
    }

    @Override
    public void addRace(Race race) {
        RaceSimulatorThread raceSimulation = applicationContext.getBean(RaceSimulatorThread.class);
        raceSimulation.setRace(race);
        taskExecutor.execute(raceSimulation);
        raceSimulations.put(race.getId(), raceSimulation);
    }

    @Override
    public void removeVehicleFromRace(Vehicle vehicle, Long raceId) {
        Optional.ofNullable(raceSimulations.get(raceId))
                .ifPresent(race -> race.removeVehicleFromRace(vehicle));
    }

    @Override
    public RaceSimulatorThread getRaceSimulation(Long raceId) {
        return Optional.ofNullable(raceSimulations.get(raceId))
                .orElseThrow(() -> new RaceNotFoundException(raceId));
    }
}
