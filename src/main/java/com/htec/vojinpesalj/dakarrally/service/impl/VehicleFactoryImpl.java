package com.htec.vojinpesalj.dakarrally.service.impl;

import com.htec.vojinpesalj.dakarrally.repository.Constants.ErrorMessages;
import com.htec.vojinpesalj.dakarrally.repository.domain.CrossMotorcycle;
import com.htec.vojinpesalj.dakarrally.repository.domain.SportMotorcycle;
import com.htec.vojinpesalj.dakarrally.repository.domain.SportsCar;
import com.htec.vojinpesalj.dakarrally.repository.domain.TerrainCar;
import com.htec.vojinpesalj.dakarrally.repository.domain.Truck;
import com.htec.vojinpesalj.dakarrally.repository.domain.Vehicle;
import com.htec.vojinpesalj.dakarrally.repository.domain.VehicleStatistic;
import com.htec.vojinpesalj.dakarrally.service.VehicleFactory;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleRequest;
import org.springframework.stereotype.Service;

@Service
public class VehicleFactoryImpl implements VehicleFactory {
    public Vehicle createVehicle(VehicleRequest vehicle) {
        switch (vehicle.getType()) {
            case SPORTS_CAR:
                return createSportsCar(vehicle);
            case TERRAIN_CAR:
                return createTerrainCar(vehicle);
            case TRUCK:
                return createTruck(vehicle);
            case CROSS_MOTORCYCLE:
                return createCrossMotorcycle(vehicle);
            case SPORT_MOTORCYCLE:
                return createSportMotorcycle(vehicle);
            default:
                throw new UnsupportedOperationException(ErrorMessages.VEHICLE_TYPE_NOT_SUPPORTED);
        }
    }

    private SportsCar createSportsCar(VehicleRequest vehicle) {
        return SportsCar.builder()
                .teamName(vehicle.getTeamName())
                .model(vehicle.getModel())
                .manufacturingDate(vehicle.getManufacturingDate())
                .vehicleStatistic(new VehicleStatistic())
                .build();
    }

    private TerrainCar createTerrainCar(VehicleRequest vehicle) {
        return TerrainCar.builder()
                .teamName(vehicle.getTeamName())
                .model(vehicle.getModel())
                .manufacturingDate(vehicle.getManufacturingDate())
                .vehicleStatistic(new VehicleStatistic())
                .build();
    }

    private Truck createTruck(VehicleRequest vehicle) {
        return Truck.builder()
                .teamName(vehicle.getTeamName())
                .model(vehicle.getModel())
                .manufacturingDate(vehicle.getManufacturingDate())
                .vehicleStatistic(new VehicleStatistic())
                .build();
    }

    private CrossMotorcycle createCrossMotorcycle(VehicleRequest vehicle) {
        return CrossMotorcycle.builder()
                .teamName(vehicle.getTeamName())
                .model(vehicle.getModel())
                .manufacturingDate(vehicle.getManufacturingDate())
                .vehicleStatistic(new VehicleStatistic())
                .build();
    }

    private SportMotorcycle createSportMotorcycle(VehicleRequest vehicle) {
        return SportMotorcycle.builder()
                .teamName(vehicle.getTeamName())
                .model(vehicle.getModel())
                .manufacturingDate(vehicle.getManufacturingDate())
                .vehicleStatistic(new VehicleStatistic())
                .build();
    }
}
