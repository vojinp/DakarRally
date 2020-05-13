package com.htec.vojinpesalj.dakarrally.service.impl;

import com.htec.vojinpesalj.dakarrally.repository.RaceRepository;
import com.htec.vojinpesalj.dakarrally.repository.domain.Race;
import com.htec.vojinpesalj.dakarrally.repository.domain.RaceStatus;
import com.htec.vojinpesalj.dakarrally.service.RaceService;
import com.htec.vojinpesalj.dakarrally.service.dto.RaceResponse;
import com.htec.vojinpesalj.dakarrally.service.mappers.RaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaceServiceImpl implements RaceService {
    private RaceRepository raceRepository;
    private RaceMapper raceMapper;

    @Autowired
    public RaceServiceImpl(RaceRepository raceRepository, RaceMapper raceMapper) {
        this.raceRepository = raceRepository;
        this.raceMapper = raceMapper;
    }

    @Override
    public RaceResponse create(Integer year) {
        Race race = Race.builder().year(year).status(RaceStatus.PENDING).build();

        return raceMapper.toDto(raceRepository.save(race));
    }
}
