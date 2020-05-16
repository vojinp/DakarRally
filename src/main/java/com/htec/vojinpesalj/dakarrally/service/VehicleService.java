package com.htec.vojinpesalj.dakarrally.service;

import com.htec.vojinpesalj.dakarrally.service.dto.FindVehicleRequest;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleRequest;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleResponse;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleStatisticResponse;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleTypeDto;
import java.util.List;

public interface VehicleService {

    /**
     * Create vehicle and add it to the race
     *
     * @param vehicleRequest vehicle's data
     * @param raceId id of the race that will contain vehicle
     * @return new vehicle from the database
     */
    VehicleResponse create(VehicleRequest vehicleRequest, Long raceId);

    /**
     * Update data on the existing vehicle
     *
     * @param vehicleRequest vehicle's new data
     * @param id of the vehicle
     * @return new vehicle from the database
     */
    VehicleResponse update(VehicleRequest vehicleRequest, Long id);

    /**
     * Delete vehicle and remove it from the race
     *
     * @param id of the vehicle
     */
    void delete(Long id);

    List<VehicleResponse> getLeaderboard(Long raceId, VehicleTypeDto type);

    /**
     * Get statistic of the vehicle
     *
     * @param vehicleId id of the vehicle
     * @return Statistic including status, malfunctions, distance and finish time
     */
    VehicleStatisticResponse getStatistic(Long vehicleId);

    /**
     * Find vehicle for the given filters
     *
     * @param findVehicleRequest filters
     * @return list of vehicles that satisfy the criteria
     */
    List<VehicleResponse> findVehicle(FindVehicleRequest findVehicleRequest);
}
