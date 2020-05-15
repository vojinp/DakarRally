package com.htec.vojinpesalj.dakarrally.service.impl;

import com.htec.vojinpesalj.dakarrally.exception.CantUpdateVehicleException;
import com.htec.vojinpesalj.dakarrally.exception.RaceNotFoundException;
import com.htec.vojinpesalj.dakarrally.exception.VehicleNotFoundException;
import com.htec.vojinpesalj.dakarrally.repository.RaceRepository;
import com.htec.vojinpesalj.dakarrally.repository.VehicleRepository;
import com.htec.vojinpesalj.dakarrally.repository.domain.RaceStatus;
import com.htec.vojinpesalj.dakarrally.repository.domain.Vehicle;
import com.htec.vojinpesalj.dakarrally.repository.specification.SearchCriteria;
import com.htec.vojinpesalj.dakarrally.repository.specification.VehicleSpecification;
import com.htec.vojinpesalj.dakarrally.service.VehicleFactory;
import com.htec.vojinpesalj.dakarrally.service.VehicleService;
import com.htec.vojinpesalj.dakarrally.service.dto.FilterOperation;
import com.htec.vojinpesalj.dakarrally.service.dto.FindVehicleRequest;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleRequest;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleResponse;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleStatisticResponse;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleTypeDto;
import com.htec.vojinpesalj.dakarrally.service.mappers.VehicleMapper;
import com.htec.vojinpesalj.dakarrally.service.mappers.VehicleStatisticMapper;
import com.htec.vojinpesalj.dakarrally.service.simulator.RaceSimulationService;
import com.htec.vojinpesalj.dakarrally.service.simulator.VehicleSimulatorThread;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService {
    private VehicleRepository vehicleRepository;
    private VehicleFactory vehicleFactory;
    private VehicleMapper vehicleMapper;
    private VehicleStatisticMapper vehicleStatisticMapper;
    private RaceRepository raceRepository;
    private RaceSimulationService raceSimulationService;

    @Autowired
    public VehicleServiceImpl(
            VehicleRepository vehicleRepository,
            VehicleFactory vehicleFactory,
            VehicleMapper vehicleMapper,
            VehicleStatisticMapper vehicleStatisticMapper,
            RaceRepository raceRepository,
            RaceSimulationService raceSimulationService) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleFactory = vehicleFactory;
        this.vehicleMapper = vehicleMapper;
        this.vehicleStatisticMapper = vehicleStatisticMapper;
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

    @Override
    public VehicleStatisticResponse getStatistic(Long id) {
        Vehicle vehicle =
                vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException(id));
        Map<Long, VehicleSimulatorThread> vehicleSimulators =
                raceSimulationService
                        .getRaceSimulation(vehicle.getRace().getId())
                        .getVehicleSimulators();
        vehicle.getVehicleStatistic()
                .setDistance(
                        vehicleSimulators.get(id).getVehicle().getVehicleStatistic().getDistance());

        return vehicleStatisticMapper.toDto(vehicle.getVehicleStatistic());
    }

    @Override
    public List<VehicleResponse> findVehicle(FindVehicleRequest findVehicleRequest) {
        List<VehicleSpecification> vehicleSpecifications =
                IntStream.range(0, findVehicleRequest.getFilterKeys().size())
                        .mapToObj(
                                i ->
                                        new VehicleSpecification(
                                                new SearchCriteria(
                                                        Arrays.asList(
                                                                findVehicleRequest
                                                                        .getFilterKeys()
                                                                        .get(i)
                                                                        .getValue()
                                                                        .split("::")),
                                                        findVehicleRequest
                                                                .getFilterValues()
                                                                .get(i))))
                        .collect(Collectors.toList());

        //        VehicleSpecification specification1 =
        //                new VehicleSpecification(
        //                        new SearchCriteria(Arrays.asList("model".split(",")), "model"));
        //        VehicleSpecification specification2 =
        //                new VehicleSpecification(
        //                        new SearchCriteria(Arrays.asList("teamName".split(",")), "team
        // 2"));
        //        List<VehicleSpecification> specs = new ArrayList<>();
        //        specs.add(specification1);
        //        specs.add(specification2);
        //        List<String> operations = Collections.singletonList("OR");

        Specification<Vehicle> vehicleSpecification =
                vehicleSpecifications.stream().findFirst().orElse(null);
        for (int i = 0; i <= vehicleSpecifications.size() - 2; i++) {
            vehicleSpecification =
                    findVehicleRequest.getFilterOperations().get(i).equals(FilterOperation.AND)
                            ? vehicleSpecification.and(vehicleSpecifications.get(i + 1))
                            : vehicleSpecification.or(vehicleSpecifications.get(i + 1));
        }
        List<Vehicle> vehicles = vehicleRepository.findAll(vehicleSpecification);

        return vehicles.stream().map(vehicleMapper::toDto).collect(Collectors.toList());
    }
}
