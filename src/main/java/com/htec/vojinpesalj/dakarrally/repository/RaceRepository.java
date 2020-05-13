package com.htec.vojinpesalj.dakarrally.repository;

import com.htec.vojinpesalj.dakarrally.repository.domain.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {}
