package com.htec.vojinpesalj.dakarrally.service.mappers;

import com.htec.vojinpesalj.dakarrally.repository.domain.Race;
import com.htec.vojinpesalj.dakarrally.service.dto.RaceResponse;

public interface RaceMapper {
  RaceResponse toDto(Race race);
}
