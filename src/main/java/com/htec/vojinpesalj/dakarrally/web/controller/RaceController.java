package com.htec.vojinpesalj.dakarrally.web.controller;

import com.htec.vojinpesalj.dakarrally.service.RaceService;
import com.htec.vojinpesalj.dakarrally.service.dto.RaceResponse;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/races")
public class RaceController {
    private RaceService raceService;

    @Autowired
    public RaceController(RaceService raceService) {
        this.raceService = raceService;
    }

    @PostMapping("")
    public ResponseEntity<RaceResponse> create(@RequestParam Integer year) throws URISyntaxException {
        RaceResponse race = raceService.create(year);

        return ResponseEntity.created(new URI(String.format("/api/races/%d", race.getId())))
                .body(race);
    }
}
