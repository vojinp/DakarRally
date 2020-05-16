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
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class RaceServiceImpl implements RaceService {
    private final RaceRepository raceRepository;
    private final RaceMapper raceMapper;
    private final RaceStatusMapper raceStatusMapper;
    private final RaceSimulationService raceSimulationService;
    private final VehicleStatusMapper vehicleStatusMapper;

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

        Race newRace = raceRepository.save(race);
        log.info(String.format("Saved race with id: %d to database.", newRace.getId()));

        return raceMapper.toDto(newRace);
    }

    @Override
    @Transactional
    public void startRace(Long id) {
        Race race = getByIdOrThrowException(id);
        if (race.getStatus() != RaceStatus.PENDING) {
            throw new RaceAlreadyStartedException(id);
        }
        race.setStatus(RaceStatus.RUNNING);
        raceRepository.save(race);
        log.info(String.format("Saved race with id: %d to database.", race.getId()));
        raceSimulationService.addRace(race);
    }

    @Override
    @Transactional
    public void finishRace(Long id) {
        Race race = getByIdOrThrowException(id);
        race.setStatus(RaceStatus.FINISHED);
        raceRepository.save(race);
        log.info(String.format("Saved race with id: %d to database.", race.getId()));
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
        Race race = raceRepository.findById(id).orElseThrow(() -> new RaceNotFoundException(id));
        log.info(String.format("Fetched race with id: %d from the database.", id));

        return race;
    }
}
