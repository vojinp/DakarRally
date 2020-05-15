package com.htec.vojinpesalj.dakarrally.service.impl;

import static java.util.stream.Collectors.groupingBy;

import com.htec.vojinpesalj.dakarrally.exception.RaceAlreadyStartedException;
import com.htec.vojinpesalj.dakarrally.exception.RaceNotFoundException;
import com.htec.vojinpesalj.dakarrally.repository.RaceRepository;
import com.htec.vojinpesalj.dakarrally.repository.domain.Race;
import com.htec.vojinpesalj.dakarrally.repository.domain.RaceStatus;
import com.htec.vojinpesalj.dakarrally.repository.domain.Vehicle;
import com.htec.vojinpesalj.dakarrally.service.RaceService;
import com.htec.vojinpesalj.dakarrally.service.dto.RaceResponse;
import com.htec.vojinpesalj.dakarrally.service.dto.RaceStatisticResponse;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleStatusDto;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleTypeDto;
import com.htec.vojinpesalj.dakarrally.service.mappers.RaceMapper;
import com.htec.vojinpesalj.dakarrally.service.mappers.RaceStatusMapper;
import com.htec.vojinpesalj.dakarrally.service.mappers.VehicleStatusMapper;
import com.htec.vojinpesalj.dakarrally.service.simulator.RaceSimulationService;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaceServiceImpl implements RaceService {
    private RaceRepository raceRepository;
    private RaceMapper raceMapper;
    private RaceStatusMapper raceStatusMapper;
    private RaceSimulationService raceSimulationService;
    private VehicleStatusMapper vehicleStatusMapper;

    @Autowired
    public RaceServiceImpl(
            RaceRepository raceRepository,
            RaceMapper raceMapper,
            RaceStatusMapper raceStatusMapper,
            RaceSimulationService raceSimulationService,
            VehicleStatusMapper vehicleStatusMapper) {
        this.raceRepository = raceRepository;
        this.raceMapper = raceMapper;
        this.raceStatusMapper = raceStatusMapper;
        this.raceSimulationService = raceSimulationService;
        this.vehicleStatusMapper = vehicleStatusMapper;
    }

    @Override
    public RaceResponse create(Integer year) {
        Race race =
                Race.builder().year(year).status(RaceStatus.PENDING).startDate(new Date()).build();

        return raceMapper.toDto(raceRepository.save(race));
    }

    @Override
    public void startRace(Long id) {
        Race race = getByIdOrThrowException(id);
        if (race.getStatus() != RaceStatus.PENDING) {
            throw new RaceAlreadyStartedException(id);
        }
        race.setStatus(RaceStatus.RUNNING);
        raceRepository.save(race);
        raceSimulationService.addRace(race);
    }

    @Override
    public void finishRace(Long id) {
        Race race = getByIdOrThrowException(id);
        race.setStatus(RaceStatus.FINISHED);
        raceRepository.save(race);
    }

    @Override
    public RaceStatisticResponse getStatistic(Long id) {
        Race race = getByIdOrThrowException(id);

        Map<VehicleStatusDto, Integer> vehiclesByStatus =
                race.getVehicles().stream()
                        .collect(groupingBy((Vehicle v) -> v.getVehicleStatistic().getStatus()))
                        .entrySet()
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        e -> vehicleStatusMapper.toDto(e.getKey()),
                                        e -> e.getValue().size()));

        Map<VehicleTypeDto, Integer> vehiclesByType =
                race.getVehicles().stream()
                        .collect(groupingBy(Vehicle::getVehicleType))
                        .entrySet()
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        e -> VehicleTypeDto.valueOf(e.getKey()),
                                        e -> e.getValue().size()));

        return RaceStatisticResponse.builder()
                .status(raceStatusMapper.toDto(race.getStatus()))
                .numberOfVehiclesByStatus(vehiclesByStatus)
                .numberOfVehiclesByType(vehiclesByType)
                .build();
    }

    private Race getByIdOrThrowException(Long id) {
        return raceRepository.findById(id).orElseThrow(() -> new RaceNotFoundException(id));
    }
}
