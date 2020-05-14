package com.htec.vojinpesalj.dakarrally.service;

import com.htec.vojinpesalj.dakarrally.service.dto.RaceResponse;
import com.htec.vojinpesalj.dakarrally.service.dto.RaceStatisticResponse;

public interface RaceService {
    RaceResponse create(Integer year);

    void startRace(Long id);

    void finishRace(Long id);

    RaceStatisticResponse getStatistic(Long id);
}
