package com.drone.droneapi.controllers;

import com.drone.droneapi.dto.DroneDto;
import com.drone.droneapi.models.Drone;
import com.drone.droneapi.services.IDroneService;
import com.drone.droneapi.utils.ApiResponse;
import com.drone.droneapi.config.MessageText;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/drone")
@Validated
public class DroneController extends BaseController {
   private final IDroneService droneService;

    @Autowired
    public DroneController(IDroneService droneService, Logger logger) {
        super(logger);
        this.droneService = droneService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> RegisterNewDrone(@Valid @RequestBody DroneDto drone){
        ApiResponse response = droneService.createDrone(drone);
        return handleApiResponse(response, MessageText.ENDPOINT_NAME_REGISTER_DRONE);
    }

    @PostMapping("load-medications/{serialNumber}")
    public ResponseEntity<ApiResponse> getDrone(@PathVariable("serialNumber") String serialNumber){
        ApiResponse response = droneService.getDrone(serialNumber);
        return handleApiResponse(response, MessageText.ENDPOINT_NAME_LOAD_MEDICATION);
    }

    @PutMapping("/{serialNumber}")
    public ResponseEntity<ApiResponse> updateDrone(@PathVariable("serialNumber") String serialNumber, @RequestBody Drone drone){
        ApiResponse response = droneService.updateDrone(serialNumber, drone);
        return handleApiResponse(response, MessageText.ENDPOINT_NAME_Change_STATE_DRONE);
    }

    @DeleteMapping("/{serialNumber}")
    public ResponseEntity<ApiResponse> deleteDrone(@PathVariable("serialNumber") String serialNumber){
        ApiResponse response = droneService.deleteDrone(serialNumber);
        return handleApiResponse(response, MessageText.ENDPOINT_NAME_Change_STATE_DRONE);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getDrones(){
        ApiResponse response = droneService.getDrones();
        return handleApiResponse(response, MessageText.ENDPOINT_NAME_Change_STATE_DRONE);
    }
}
