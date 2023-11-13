package com.drone.droneapi.services;

import com.drone.droneapi.utils.ApiResponse;

public interface IPeriodicTaskLogService {
    ApiResponse periodicTaskLogs();
    ApiResponse getAllLogsBySerialNumber(String serialNumber);
}
