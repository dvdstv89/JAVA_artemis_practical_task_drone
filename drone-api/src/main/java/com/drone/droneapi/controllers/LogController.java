package com.drone.droneapi.controllers;

import com.drone.droneapi.config.MessageText;
import com.drone.droneapi.services.IPeriodicTaskLogService;
import com.drone.droneapi.utils.ApiResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/log")
public class LogController extends BaseController {
   private final IPeriodicTaskLogService periodicTaskLogService;

    @Autowired
    public LogController(IPeriodicTaskLogService periodicTaskLogService, Logger logger) {
        super(logger);
        this.periodicTaskLogService = periodicTaskLogService;
    }

    @GetMapping("/{serialNumber}")
    public ResponseEntity<ApiResponse> getDrones(@PathVariable("serialNumber") String serialNumber){
        ApiResponse response = periodicTaskLogService.getAllLogsBySerialNumber(serialNumber);
        return handleApiResponse(response, MessageText.ENDPOINT_NAME_SHOW_LOGS);
    }
}
