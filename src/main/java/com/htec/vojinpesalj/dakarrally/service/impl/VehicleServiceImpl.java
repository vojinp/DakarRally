package com.htec.vojinpesalj.dakarrally.service.impl;

import com.htec.vojinpesalj.dakarrally.exception.RaceNotFoundException;
import com.htec.vojinpesalj.dakarrally.repository.RaceRepository;
import com.htec.vojinpesalj.dakarrally.repository.VehicleRepository;
import com.htec.vojinpesalj.dakarrally.repository.domain.Vehicle;
import com.htec.vojinpesalj.dakarrally.service.VehicleFactory;
import com.htec.vojinpesalj.dakarrally.service.VehicleService;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleRequest;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleResponse;
import com.htec.vojinpesalj.dakarrally.service.mappers.VehicleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService {
    private VehicleRepository vehicleRepository;
    private VehicleFactory vehicleFactory;
    private VehicleMapper vehicleMapper;
    private RaceRepository raceRepository;

    @Autowired
    public VehicleServiceImpl(
            VehicleRepository vehicleRepository,
            VehicleFactory vehicleFactory,
            VehicleMapper vehicleMapper,
            RaceRepository raceRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleFactory = vehicleFactory;
        this.vehicleMapper = vehicleMapper;
        this.raceRepository = raceRepository;
    }

    @Override
    public VehicleResponse create(VehicleRequest vehicleRequest, Long raceId) {
        Vehicle vehicle = vehicleFactory.createVehicle(vehicleRequest);
        vehicle.setRace(
                raceRepository
                        .findById(raceId)
                        .orElseThrow(() -> new RaceNotFoundException(raceId)));
        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return vehicleMapper.toDto(savedVehicle);
    }
}
