package com.htec.vojinpesalj.dakarrally.service.impl;

import com.htec.vojinpesalj.dakarrally.exception.RaceNotFoundException;
import com.htec.vojinpesalj.dakarrally.repository.RaceRepository;
import com.htec.vojinpesalj.dakarrally.repository.domain.Race;
import com.htec.vojinpesalj.dakarrally.repository.domain.RaceStatus;
import com.htec.vojinpesalj.dakarrally.service.RaceService;
import com.htec.vojinpesalj.dakarrally.service.dto.RaceResponse;
import com.htec.vojinpesalj.dakarrally.service.mappers.RaceMapper;
import com.htec.vojinpesalj.dakarrally.service.simulator.RaceSimulationService;
import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaceServiceImpl implements RaceService {
    private RaceRepository raceRepository;
    private RaceMapper raceMapper;
    private RaceSimulationService raceSimulationService;

    @Autowired
    public RaceServiceImpl(
            RaceRepository raceRepository,
            RaceMapper raceMapper,
            RaceSimulationService raceSimulationService) {
        this.raceRepository = raceRepository;
        this.raceMapper = raceMapper;
        this.raceSimulationService = raceSimulationService;
    }

    @Override
    @Transactional
    public RaceResponse create(Integer year) {
        Race race =
                Race.builder().year(year).status(RaceStatus.PENDING).startDate(new Date()).build();

        return raceMapper.toDto(raceRepository.save(race));
    }

    @Override
    @Transactional
    public void startRace(Long id) {
        Race race = raceRepository.findById(id).orElseThrow(() -> new RaceNotFoundException(id));
        race.setStatus(RaceStatus.RUNNING);
        raceRepository.save(race);
        raceSimulationService.addRace(race);
    }

    @Override
    @Transactional
    public void finishRace(Long id) {
        Race race = raceRepository.findById(id).orElseThrow(() -> new RaceNotFoundException(id));
        race.setStatus(RaceStatus.FINISHED);
        raceRepository.save(race);
    }
}
