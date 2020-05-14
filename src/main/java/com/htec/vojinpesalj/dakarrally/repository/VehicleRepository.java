package com.htec.vojinpesalj.dakarrally.repository;

import com.htec.vojinpesalj.dakarrally.repository.domain.Vehicle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByRaceId(Long raceId);

    List<Vehicle> findByRaceIdAndVehicleType(Long raceId, String vehicleType);
}
