package com.htec.vojinpesalj.dakarrally.web.controller;

import com.htec.vojinpesalj.dakarrally.service.VehicleService;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleRequest;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleResponse;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleStatisticResponse;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleTypeDto;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
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
public class VehicleController {
    private VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PutMapping("/vehicles/{id}")
    public ResponseEntity<VehicleResponse> update(
            @PathVariable Long id, @Valid @RequestBody VehicleRequest vehicleRequest) {
        VehicleResponse vehicle = vehicleService.update(vehicleRequest, id);

        return ResponseEntity.ok(vehicle);
    }

    @DeleteMapping("/vehicles/{id}")
    public ResponseEntity<VehicleResponse> delete(@PathVariable Long id) {
        vehicleService.delete(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("vehicles/{vehicleId}/statistic")
    public ResponseEntity<VehicleStatisticResponse> getStatistic(@PathVariable Long vehicleId) {
        VehicleStatisticResponse vehicleStatistic = vehicleService.getStatistic(vehicleId);

        return ResponseEntity.ok(vehicleStatistic);
    }

    @PostMapping("/races/{raceId}/vehicles")
    public ResponseEntity<VehicleResponse> create(
            @PathVariable Long raceId, @Valid @RequestBody VehicleRequest vehicleRequest)
            throws URISyntaxException {
        VehicleResponse vehicle = vehicleService.create(vehicleRequest, raceId);

        return ResponseEntity.created(new URI(String.format("/api/vehicles/%d", vehicle.getId())))
                .body(vehicle);
    }

    @GetMapping("races/{raceId}/vehicles/leaderboard")
    public ResponseEntity<List<VehicleResponse>> getLeaderboard(
            @RequestParam(required = false) VehicleTypeDto type, @PathVariable Long raceId) {
        List<VehicleResponse> leaderboard = vehicleService.getLeaderboard(raceId, type);

        return ResponseEntity.ok(leaderboard);
    }
}
