package com.htec.vojinpesalj.dakarrally.web.controller;

import com.htec.vojinpesalj.dakarrally.service.VehicleService;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleRequest;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleResponse;
import java.net.URI;
import java.net.URISyntaxException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class VehicleController {
    private VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/races/{raceId}/vehicles")
    public ResponseEntity<VehicleResponse> create(
            @PathVariable Long raceId, @Valid @RequestBody VehicleRequest vehicleRequest)
            throws URISyntaxException {
        VehicleResponse vehicle = vehicleService.create(vehicleRequest, raceId);

        return ResponseEntity.created(new URI(String.format("/api/vehicles/%d", vehicle.getId())))
                .body(vehicle);
    }
}
