package com.drone.droneapi.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class LocalNotFoundException extends BaseException {

    public LocalNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
