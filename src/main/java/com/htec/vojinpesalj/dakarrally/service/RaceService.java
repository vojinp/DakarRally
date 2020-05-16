package com.htec.vojinpesalj.dakarrally.service;

import com.htec.vojinpesalj.dakarrally.service.dto.RaceResponse;
import com.htec.vojinpesalj.dakarrally.service.dto.RaceStatisticResponse;

public interface RaceService {

    /**
     * Create new race and add it to the database
     *
     * @param year when the race is held
     * @return Newly created race from the database
     */
    RaceResponse create(Integer year);

    /**
     * Start race for the given id
     *
     * @param id of the race that is going to start
     */
    void startRace(Long id);

    /**
     * Finish the race for the given id
     *
     * @param id of the race
     */
    void finishRace(Long id);

    /**
     * Get statistic of the race from the database
     *
     * @param id of the race
     * @return Statistic of the race including the status, number of vehicle by status and type
     */
    RaceStatisticResponse getStatistic(Long id);
}
