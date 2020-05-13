package com.htec.vojinpesalj.dakarrally.repository;

import com.htec.vojinpesalj.dakarrally.repository.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {}
