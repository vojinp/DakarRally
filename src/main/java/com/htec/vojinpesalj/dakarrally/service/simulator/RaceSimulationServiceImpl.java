package com.htec.vojinpesalj.dakarrally.service.simulator;

import com.htec.vojinpesalj.dakarrally.repository.VehicleRepository;
import com.htec.vojinpesalj.dakarrally.repository.domain.Race;
import com.htec.vojinpesalj.dakarrally.repository.domain.Vehicle;
import com.htec.vojinpesalj.dakarrally.service.RaceService;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RaceSimulationServiceImpl implements RaceSimulationService {
    private Map<Long, RaceSimulatorThread> raceSimulations;
    private RaceService raceService;
    private VehicleRepository vehicleRepository;

    public RaceSimulationServiceImpl(RaceService raceService, VehicleRepository vehicleRepository) {
        this.raceSimulations = new HashMap<>();
        this.raceService = raceService;
        this.raceSimulations = new HashMap<>();
    }

    @Override
    public void addRace(Race race) {
        RaceSimulatorThread raceSimulation =
                new RaceSimulatorThread(race, raceService, vehicleRepository);
        raceSimulation.start();
        raceSimulations.put(race.getId(), raceSimulation);
    }

    @Override
    public void removeVehicleFromRace(Vehicle vehicle, Long raceId) {
        Optional.ofNullable(raceSimulations.get(raceId))
                .ifPresent(race -> race.removeVehicleFromRace(vehicle));
    }
}
