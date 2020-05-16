package com.htec.vojinpesalj.dakarrally.service.mappers;

import com.htec.vojinpesalj.dakarrally.repository.domain.Race;
import com.htec.vojinpesalj.dakarrally.service.dto.RaceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaceMapperImpl implements RaceMapper {
    private final RaceStatusMapper raceStatusMapper;

    @Autowired
    public RaceMapperImpl(RaceStatusMapper raceStatusMapper) {
        this.raceStatusMapper = raceStatusMapper;
    }

    @Override
    public RaceResponse toDto(Race race) {
        return RaceResponse.builder()
                .id(race.getId())
                .year(race.getYear())
                .status(raceStatusMapper.toDto(race.getStatus()))
                .build();
    }
}
