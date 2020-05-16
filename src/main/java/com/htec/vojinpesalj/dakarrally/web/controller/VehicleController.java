package com.htec.vojinpesalj.dakarrally.web.controller;

import com.htec.vojinpesalj.dakarrally.service.VehicleService;
import com.htec.vojinpesalj.dakarrally.service.dto.FindVehicleRequest;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleRequest;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleResponse;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleStatisticResponse;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleTypeDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Log4j2
@Api(
        value = "Vehicle Management System",
        description = "Operations pertaining to vehicle in Vehicle Management System")
public class VehicleController {
    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PutMapping("/vehicles/{id}")
    @ApiOperation(value = "Update properties of the vehicle", response = VehicleResponse.class)
    public ResponseEntity<VehicleResponse> update(
            @ApiParam(value = "Vehicle Id to update vehicle object", required = true) @PathVariable
                    Long id,
            @ApiParam(value = "Update vehicle object", required = true) @Valid @RequestBody
                    VehicleRequest vehicleRequest) {
        log.info(String.format("PUT /api/vehicles/%d", id));
        VehicleResponse vehicle = vehicleService.update(vehicleRequest, id);

        return ResponseEntity.ok(vehicle);
    }

    @DeleteMapping("/vehicles/{id}")
    @ApiOperation(value = "Remove vehicle from the race")
    public ResponseEntity delete(
            @ApiParam(value = "Vehicle Id to remove from the race", required = true) @PathVariable
                    Long id) {
        log.info(String.format("DELETE /api/vehicles/%d", id));
        vehicleService.delete(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("vehicles/filter")
    @ApiOperation(value = "Find vehicle", response = List.class)
    public ResponseEntity<List<VehicleResponse>> finVehicle(
            @ApiParam(value = "Filters that are used to find vehicle", required = true)
                    @Valid
                    @RequestBody
                    FindVehicleRequest findVehicleRequest) {
        log.info("PUT /api/vehicles/filter");
        List<VehicleResponse> vehicles = vehicleService.findVehicle(findVehicleRequest);

        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("vehicles/{vehicleId}/statistic")
    @ApiOperation(value = "Get statistic of the vehicle", response = VehicleStatisticResponse.class)
    public ResponseEntity<VehicleStatisticResponse> getStatistic(
            @ApiParam(value = "Id of the vehicle to get statistic", required = true) @PathVariable
                    Long vehicleId) {
        log.info(String.format("GET /api/vehicles/%d/statistic", vehicleId));
        VehicleStatisticResponse vehicleStatistic = vehicleService.getStatistic(vehicleId);

        return ResponseEntity.ok(vehicleStatistic);
    }

    @PostMapping("/races/{raceId}/vehicles")
    @ApiOperation(value = "Add new vehicle to the race", response = VehicleResponse.class)
    public ResponseEntity<VehicleResponse> create(
            @ApiParam(value = "Id of the race", required = true) @PathVariable Long raceId,
            @ApiParam(value = "Body of the vehicle", required = true) @Valid @RequestBody
                    VehicleRequest vehicleRequest)
            throws URISyntaxException {
        log.info(String.format("POST /api/races/%d/vehicles", raceId));
        VehicleResponse vehicle = vehicleService.create(vehicleRequest, raceId);

        return ResponseEntity.created(new URI(String.format("/api/vehicles/%d", vehicle.getId())))
                .body(vehicle);
    }

    @GetMapping("races/{raceId}/vehicles/leaderboard")
    @ApiOperation(value = "Get the leaderboard of the race", response = List.class)
    public ResponseEntity<List<VehicleResponse>> getLeaderboard(
            @ApiParam(value = "Type of the vehicle", required = true)
                    @RequestParam(required = false)
                    VehicleTypeDto type,
            @ApiParam(value = "Id of the race", required = true) @PathVariable Long raceId) {
        log.info(String.format("GET /api/races/%d/vehicles/leaderboard", raceId));
        List<VehicleResponse> leaderboard = vehicleService.getLeaderboard(raceId, type);

        return ResponseEntity.ok(leaderboard);
    }
}
