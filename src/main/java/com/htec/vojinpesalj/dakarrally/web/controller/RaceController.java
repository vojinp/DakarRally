package com.htec.vojinpesalj.dakarrally.web.controller;

import com.htec.vojinpesalj.dakarrally.service.RaceService;
import com.htec.vojinpesalj.dakarrally.service.dto.RaceResponse;
import com.htec.vojinpesalj.dakarrally.service.dto.RaceStatisticResponse;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/races")
@Log4j2
public class RaceController {
    private RaceService raceService;

    @Autowired
    public RaceController(RaceService raceService) {
        this.raceService = raceService;
    }

    @PostMapping("")
    public ResponseEntity<RaceResponse> create(@RequestParam Integer year)
            throws URISyntaxException {
        log.info("POST /api/races");
        RaceResponse race = raceService.create(year);

        return ResponseEntity.created(new URI(String.format("/api/races/%d", race.getId())))
                .body(race);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RaceResponse> start(@PathVariable Long id) {
        log.info(String.format("PUT /api/races/%d", id));
        raceService.startRace(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/statistic")
    public ResponseEntity<RaceStatisticResponse> getStatus(@PathVariable Long id) {
        log.info(String.format("GET /api/races/%d/statistic", id));
        RaceStatisticResponse raceStatisticResponse = raceService.getStatistic(id);

        return ResponseEntity.ok(raceStatisticResponse);
    }
}
