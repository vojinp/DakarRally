package com.htec.vojinpesalj.dakarrally.service.simulator;

import com.htec.vojinpesalj.dakarrally.repository.domain.Race;
import com.htec.vojinpesalj.dakarrally.repository.domain.Vehicle;

public interface RaceSimulationService {
    void addRace(Race race);

    void removeVehicleFromRace(Vehicle vehicle, Long raceId);

    RaceSimulatorThread getRaceSimulation(Long raceId);
}
