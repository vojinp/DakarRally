package com.htec.vojinpesalj.dakarrally.service.simulator;

import com.htec.vojinpesalj.dakarrally.repository.domain.Race;
import com.htec.vojinpesalj.dakarrally.repository.domain.Vehicle;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RaceSimulationServiceImpl implements RaceSimulationService {
    private Map<Long, RaceSimulatorThread> raceSimulations;

    public RaceSimulationServiceImpl() {
        this.raceSimulations = new HashMap<>();
    }

    @Override
    public void addRace(Race race) {
        RaceSimulatorThread raceSimulation = new RaceSimulatorThread(race);
        raceSimulation.start();
        raceSimulations.put(race.getId(), raceSimulation);
    }

    @Override
    public void removeVehicleFromRace(Vehicle vehicle, Long raceId) {
        Optional.ofNullable(raceSimulations.get(raceId))
                .ifPresent(race -> race.removeVehicleFromRace(vehicle));
    }
}
