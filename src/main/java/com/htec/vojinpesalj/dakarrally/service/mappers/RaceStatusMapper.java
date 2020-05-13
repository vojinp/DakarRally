package com.htec.vojinpesalj.dakarrally.service.mappers;

import com.htec.vojinpesalj.dakarrally.repository.domain.RaceStatus;
import com.htec.vojinpesalj.dakarrally.service.dto.RaceStatusDto;

public interface RaceStatusMapper {
    RaceStatus toEntity(RaceStatusDto raceStatus);

    RaceStatusDto toDto(RaceStatus raceStatus);
}
