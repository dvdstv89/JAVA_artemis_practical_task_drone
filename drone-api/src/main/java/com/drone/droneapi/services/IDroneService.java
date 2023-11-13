package com.drone.droneapi.services;

import com.drone.droneapi.dto.DroneDto;
import com.drone.droneapi.models.Drone;
import com.drone.droneapi.utils.ApiResponse;

import java.util.List;

public interface IDroneService{
    ApiResponse createDrone(DroneDto drone);
    ApiResponse getDrone(String serialNumber);
    ApiResponse updateDrone(String serialNumber, Drone drone);
    ApiResponse deleteDrone(String serialNumber);
    ApiResponse getDrones();
}
