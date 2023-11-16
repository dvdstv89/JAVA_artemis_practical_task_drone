package com.drone.droneapi.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class LocalBadRequestException extends BaseException {

    public LocalBadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
