package com.drone.droneapi.controllers;

import com.drone.droneapi.dto.DroneBatteryLevelDto;
import com.drone.droneapi.dto.DroneDto;
import com.drone.droneapi.dto.DroneMedicationDto;
import com.drone.droneapi.dto.DroneStateOutputDto;
import com.drone.droneapi.error.*;
import com.drone.droneapi.services.IDroneService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/drone")
//@Validated
public class DroneController extends BaseController {
    private final IDroneService droneService;

    @Autowired
    public DroneController(IDroneService droneService, Logger logger) {
        super(logger);
        this.droneService = droneService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> registerNewDrone(@RequestBody DroneDto drone, BindingResult bindingResult) throws LocalBadRequestException {
        if (bindingResult.hasErrors()) {
           ApiResponse api = new ApiResponse(bindingResult);
            return handleApiResponse(api, MessageText.ENDPOINT_NAME_REGISTER_DRONE);
        }
        ApiResponse response = droneService.createDrone(drone);
        return handleApiResponse(response, MessageText.ENDPOINT_NAME_REGISTER_DRONE);
    }

    @PostMapping("load-medications/{serialNumber}")
    public ResponseEntity<ApiResponse> loadMedicationsIntoDrone(@PathVariable("serialNumber") String serialNumber, @Valid @RequestBody List<DroneMedicationDto> droneMedication) throws LocalNotFoundException, LocalBadRequestException, LocalIlegalArgumentRequestException {


        ApiResponse response = droneService.loadMedicationsIntoDrone(serialNumber, droneMedication);
        return handleApiResponse(response, MessageText.ENDPOINT_NAME_LOAD_MEDICATION);
    }

    @GetMapping("check-load-medications/{serialNumber}")
    public ResponseEntity<ApiResponse> checkLoadMedicationsIntoDrone(@PathVariable("serialNumber") String serialNumber) throws LocalNotFoundException, LocalBadRequestException {
        ApiResponse response = droneService.checkLoadedMedicationsIntoDrone(serialNumber);
        return handleApiResponse(response, MessageText.ENDPOINT_NAME_CHECK_LOAD_MEDICATION);
    }

    @GetMapping("check-available-drone-for-loading")
    public ResponseEntity<ApiResponse> checkAvailableForLoading() throws LocalNotFoundException, LocalBadRequestException {
        ApiResponse response = droneService.getDronesAvailablesForLoading();
        return handleApiResponse(response, MessageText.ENDPOINT_NAME_CHECK_AVAILABLE_DRONES);
    }

    @GetMapping("check-battery-level/{serialNumber}")
    public ResponseEntity<ApiResponse> checkBatteryLevel(@PathVariable("serialNumber") String serialNumber) throws LocalNotFoundException, LocalBadRequestException {
        ApiResponse response = droneService.checkBatteryCapacity(serialNumber);
        return handleApiResponse(response, MessageText.ENDPOINT_NAME_CHECK_BATTERY_LEVEL_DRONE);
    }

    @PostMapping("change-battery-level/{serialNumber}")
    public ResponseEntity<ApiResponse> changeBatteryLevel(@PathVariable("serialNumber") String serialNumber, @RequestBody DroneBatteryLevelDto drone) throws LocalNotFoundException, LocalBadRequestException {
        ApiResponse response = droneService.changeBatteryCapacity(serialNumber, drone);
        return handleApiResponse(response, MessageText.ENDPOINT_NAME_Change_BATTERY_LEVEL_DRONE);
    }


    @PostMapping("change-state/{serialNumber}")
    public ResponseEntity<ApiResponse> changeDroneState(@PathVariable("serialNumber") String serialNumber, @RequestBody DroneStateOutputDto drone) throws LocalNotFoundException, LocalBadRequestException {
        ApiResponse response = droneService.changeDroneState(serialNumber, drone);
        return handleApiResponse(response, MessageText.ENDPOINT_NAME_Change_STATE_DRONE);
    }


    @GetMapping("/{serialNumber}")
    public ResponseEntity<ApiResponse> getDrone(@PathVariable("serialNumber") String serialNumber) throws LocalNotFoundException, LocalBadRequestException{
        ApiResponse response = droneService.getDronebySerialNumbre(serialNumber);
        return handleApiResponse(response, MessageText.ENDPOINT_NAME_GET_DRONE);
    }

    @DeleteMapping("/{serialNumber}")
    public ResponseEntity<ApiResponse> deleteDrone(@PathVariable("serialNumber") String serialNumber) throws LocalNotFoundException, LocalBadRequestException{
        ApiResponse response = droneService.deleteDrone(serialNumber);
        return handleApiResponse(response, MessageText.ENDPOINT_NAME_DELETE_DRONE);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getDrones() throws LocalBadRequestException {
        ApiResponse response = droneService.getDrones();
        return handleApiResponse(response, MessageText.ENDPOINT_NAME_GET_ALL_DRONE);
    }
}
