package com.drone.droneapi.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class LocalIlegalArgumentRequestException extends BaseException {
    public LocalIlegalArgumentRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
