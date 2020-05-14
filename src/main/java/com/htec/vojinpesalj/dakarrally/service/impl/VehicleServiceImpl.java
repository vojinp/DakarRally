package com.htec.vojinpesalj.dakarrally.service.impl;

import com.htec.vojinpesalj.dakarrally.exception.CantUpdateVehicleException;
import com.htec.vojinpesalj.dakarrally.exception.RaceNotFoundException;
import com.htec.vojinpesalj.dakarrally.exception.VehicleNotFoundException;
import com.htec.vojinpesalj.dakarrally.repository.RaceRepository;
import com.htec.vojinpesalj.dakarrally.repository.VehicleRepository;
import com.htec.vojinpesalj.dakarrally.repository.domain.RaceStatus;
import com.htec.vojinpesalj.dakarrally.repository.domain.Vehicle;
import com.htec.vojinpesalj.dakarrally.service.VehicleFactory;
import com.htec.vojinpesalj.dakarrally.service.VehicleService;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleRequest;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleResponse;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleTypeDto;
import com.htec.vojinpesalj.dakarrally.service.mappers.VehicleMapper;
import com.htec.vojinpesalj.dakarrally.service.simulator.RaceSimulationService;
import com.htec.vojinpesalj.dakarrally.service.simulator.VehicleSimulatorThread;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService {
    private VehicleRepository vehicleRepository;
    private VehicleFactory vehicleFactory;
    private VehicleMapper vehicleMapper;
    private RaceRepository raceRepository;
    private RaceSimulationService raceSimulationService;

    @Autowired
    public VehicleServiceImpl(
            VehicleRepository vehicleRepository,
            VehicleFactory vehicleFactory,
            VehicleMapper vehicleMapper,
            RaceRepository raceRepository,
            RaceSimulationService raceSimulationService) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleFactory = vehicleFactory;
        this.vehicleMapper = vehicleMapper;
        this.raceRepository = raceRepository;
        this.raceSimulationService = raceSimulationService;
    }

    @Override
    public VehicleResponse create(VehicleRequest vehicleRequest, Long raceId) {
        Vehicle vehicle = vehicleFactory.createVehicle(vehicleRequest);
        vehicle.setRace(
                raceRepository
                        .findById(raceId)
                        .orElseThrow(() -> new RaceNotFoundException(raceId)));
        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return vehicleMapper.toDto(savedVehicle);
    }

    @Override
    public VehicleResponse update(VehicleRequest vehicleRequest, Long id) {
        Vehicle vehicle =
                vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException(id));

        if (vehicle.getRace().getStatus() != RaceStatus.PENDING) {
            throw new CantUpdateVehicleException(id);
        }

        Vehicle newVehicle = vehicleFactory.createVehicle(vehicleRequest);
        newVehicle.setRace(vehicle.getRace());
        newVehicle.setId(id);
        vehicleRepository.save(newVehicle);

        return vehicleMapper.toDto(newVehicle);
    }

    @Override
    public void delete(Long id) {
        Vehicle vehicle =
                vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException(id));
        vehicleRepository.deleteById(id);
        if (vehicle.getRace().getStatus() == RaceStatus.RUNNING) {
            raceSimulationService.removeVehicleFromRace(vehicle, vehicle.getRace().getId());
        }
    }

    @Override
    public List<VehicleResponse> getLeaderboard(Long raceId, VehicleTypeDto type) {
        List<Vehicle> vehicles =
                Optional.ofNullable(type)
                        .map(t -> vehicleRepository.findByRaceIdAndVehicleType(raceId, t.name()))
                        .orElseGet(() -> vehicleRepository.findByRaceId(raceId));

        vehicleRepository.findByRaceId(raceId);
        Map<Long, VehicleSimulatorThread> vehicleSimulators =
                raceSimulationService.getRaceSimulation(raceId).getVehicleSimulators();
        vehicles.forEach(
                v ->
                        v.getVehicleStatistic()
                                .setDistance(
                                        vehicleSimulators
                                                .get(v.getId())
                                                .getVehicle()
                                                .getVehicleStatistic()
                                                .getDistance()));
        vehicles.sort(
                Comparator.comparing((Vehicle v) -> v.getVehicleStatistic().getDistance())
                        .reversed());

        return vehicles.stream().map(vehicleMapper::toDto).collect(Collectors.toList());
    }
}
