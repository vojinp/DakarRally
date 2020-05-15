package com.htec.vojinpesalj.dakarrally.service;

import com.htec.vojinpesalj.dakarrally.service.dto.FindVehicleRequest;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleRequest;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleResponse;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleStatisticResponse;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleTypeDto;
import java.util.List;

public interface VehicleService {
    VehicleResponse create(VehicleRequest vehicleRequest, Long raceId);

    VehicleResponse update(VehicleRequest vehicleRequest, Long id);

    void delete(Long id);

    List<VehicleResponse> getLeaderboard(Long raceId, VehicleTypeDto type);

    VehicleStatisticResponse getStatistic(Long vehicleId);

    List<VehicleResponse> findVehicle(FindVehicleRequest findVehicleRequest);
}
