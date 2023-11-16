package com.drone.droneapi.services;

import com.drone.droneapi.dto.DroneBatteryLevelDto;
import com.drone.droneapi.dto.DroneDto;
import com.drone.droneapi.dto.DroneMedicationDto;
import com.drone.droneapi.dto.DroneStateOutputDto;
import com.drone.droneapi.error.ApiResponse;
import com.drone.droneapi.error.LocalBadRequestException;
import com.drone.droneapi.error.LocalIlegalArgumentRequestException;
import com.drone.droneapi.error.LocalNotFoundException;

import java.util.List;

public interface IDroneService {
    ApiResponse createDrone(DroneDto drone) throws LocalBadRequestException;

    ApiResponse loadMedicationsIntoDrone(String serialNumber, List<DroneMedicationDto> droneMedication) throws LocalNotFoundException, LocalBadRequestException, LocalIlegalArgumentRequestException;

    ApiResponse checkLoadedMedicationsIntoDrone(String serialNumber) throws LocalBadRequestException, LocalNotFoundException;

    ApiResponse getDronesAvailablesForLoading() throws LocalNotFoundException, LocalBadRequestException;

    ApiResponse changeDroneState(String serialNumber, DroneStateOutputDto drone) throws LocalNotFoundException, LocalBadRequestException;

    ApiResponse checkBatteryCapacity(String serialNumber) throws LocalBadRequestException, LocalNotFoundException;

    ApiResponse changeBatteryCapacity(String serialNumber, DroneBatteryLevelDto drone) throws LocalNotFoundException, LocalBadRequestException;

    ApiResponse getDronebySerialNumbre(String serialNumber) throws LocalBadRequestException, LocalNotFoundException;

    ApiResponse deleteDrone(String serialNumber) throws LocalBadRequestException, LocalNotFoundException;

    ApiResponse getDrones() throws LocalBadRequestException;
}
