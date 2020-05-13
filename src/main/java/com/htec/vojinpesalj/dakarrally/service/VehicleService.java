package com.htec.vojinpesalj.dakarrally.service;

import com.htec.vojinpesalj.dakarrally.service.dto.VehicleRequest;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleResponse;

public interface VehicleService {
  VehicleResponse create(VehicleRequest vehicleRequest, Long raceId);
  VehicleResponse update(VehicleRequest vehicleRequest, Long id);
  void delete(Long id);
}
