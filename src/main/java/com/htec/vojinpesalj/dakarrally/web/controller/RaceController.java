package com.htec.vojinpesalj.dakarrally.web.controller;

import com.htec.vojinpesalj.dakarrally.service.RaceService;
import com.htec.vojinpesalj.dakarrally.service.dto.RaceResponse;
import com.htec.vojinpesalj.dakarrally.service.dto.RaceStatisticResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
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
@Api(
        value = "Race Management System",
        description = "Operations pertaining to race in Race Management System")
public class RaceController {
    private final RaceService raceService;

    @Autowired
    public RaceController(RaceService raceService) {
        this.raceService = raceService;
    }

    @PostMapping("")
    @ApiOperation(
            value = "Create new race",
            response = RaceResponse.class,
            authorizations = {@Authorization(value = "jwtToken")})
    public ResponseEntity<RaceResponse> create(
            @ApiParam(value = "Year when race is held", required = true) @RequestParam Integer year)
            throws URISyntaxException {
        log.info("POST /api/races");
        RaceResponse race = raceService.create(year);

        return ResponseEntity.created(new URI(String.format("/api/races/%d", race.getId())))
                .body(race);
    }

    @PutMapping("/{id}")
    @ApiOperation(
            value = "Start the race",
            authorizations = {@Authorization(value = "jwtToken")})
    public ResponseEntity start(
            @ApiParam(value = "Id of the race which is going to start", required = true)
                    @PathVariable
                    Long id) {
        log.info(String.format("PUT /api/races/%d", id));
        raceService.startRace(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/statistic")
    @ApiOperation(
            value = "View statistic of the race",
            response = RaceStatisticResponse.class,
            authorizations = {@Authorization(value = "jwtToken")})
    public ResponseEntity<RaceStatisticResponse> getStatus(
            @ApiParam(value = "Id of the race for statistic", required = true) @PathVariable
                    Long id) {
        log.info(String.format("GET /api/races/%d/statistic", id));
        RaceStatisticResponse raceStatisticResponse = raceService.getStatistic(id);

        return ResponseEntity.ok(raceStatisticResponse);
    }
}
