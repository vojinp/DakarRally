package com.htec.vojinpesalj.dakarrally.service;

import com.htec.vojinpesalj.dakarrally.service.dto.RaceResponse;

public interface RaceService {
    RaceResponse create(Integer year);

    void startRace(Long id);
}
