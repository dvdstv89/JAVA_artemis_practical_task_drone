package com.drone.droneapi.services;

import com.drone.droneapi.error.ApiResponse;
import com.drone.droneapi.error.LocalBadRequestException;
import com.drone.droneapi.error.LocalIlegalArgumentRequestException;
import com.drone.droneapi.error.LocalNotFoundException;

public interface IPeriodicTaskLogService {
    ApiResponse periodicTaskLogs() throws LocalBadRequestException, LocalNotFoundException;
    ApiResponse getAllLogsBySerialNumber(String serialNumber) throws LocalBadRequestException, LocalIlegalArgumentRequestException, LocalNotFoundException;
}
