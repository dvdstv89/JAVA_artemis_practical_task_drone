package com.drone.droneapi.controllers;

import com.drone.droneapi.utils.ApiResponse;
import com.drone.droneapi.config.MessageText;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {
    private final Logger logger;
    public BaseController(Logger logger)
    {
        this.logger = logger;
    }

    protected ResponseEntity<ApiResponse> handleApiResponse(ApiResponse response, String endpoint) {
        switch (response.getStatusCode()) {
            case OK:
                logInfo(String.format(MessageText.HANDLE_API_RESPONSE_OK, endpoint));
                break;
            case CREATED:
                logInfo(String.format(MessageText.HANDLE_API_RESPONSE_CREATED, endpoint));
                break;
            case NOT_FOUND:
                logInfo(String.format(MessageText.HANDLE_API_RESPONSE_NOT_FOUND, endpoint));
                break;
            default:
                logError(String.format(MessageText.HANDLE_API_RESPONSE_BAD_RESPONSE, endpoint));
                break;
        }
        return new ResponseEntity<>(response, response.getStatusCode());
    }

    protected ResponseEntity<ApiResponse> handleBindingResult(BindingResult bindingResult){
        ApiResponse response = new ApiResponse(bindingResult);
        return new ResponseEntity<>(response, response.getStatusCode());
    }

    private void logInfo(String message) {
        if (logger != null) {
            logger.info(message);
        }
    }

    private void logError(String message) {
        if (logger != null) {
            logger.error(message);
        }
    }
}
