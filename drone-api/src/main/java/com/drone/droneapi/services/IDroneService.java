package com.drone.droneapi.services;

import com.drone.droneapi.dto.DroneBatteryLevelDto;
import com.drone.droneapi.dto.DroneDto;
import com.drone.droneapi.dto.DroneMedicationDto;
import com.drone.droneapi.dto.DroneStateOutputDto;
import com.drone.droneapi.utils.ApiResponse;

import java.util.List;

public interface IDroneService {
    ApiResponse createDrone(DroneDto drone);

    ApiResponse loadMedicationsIntoDrone(String serialNumber, List<DroneMedicationDto> droneMedication);

    ApiResponse checkLoadedMedicationsIntoDrone(String serialNumber);

    ApiResponse getDronesAvailablesForLoading();

    ApiResponse changeDroneState(String serialNumber, DroneStateOutputDto drone);

    ApiResponse checkBatteryCapacity(String serialNumber);

    ApiResponse changeBatteryCapacity(String serialNumber, DroneBatteryLevelDto drone);

    ApiResponse getDronebySerialNumbre(String serialNumber);

    ApiResponse deleteDrone(String serialNumber);

    ApiResponse getDrones();
}
