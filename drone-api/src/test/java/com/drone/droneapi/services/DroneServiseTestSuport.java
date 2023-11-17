package com.drone.droneapi.services;

import org.springframework.http.HttpStatus;

import java.util.stream.Stream;

public class DroneServiseTestSuport {
    public static Stream<Object[]> getDronesTestCases() {
        return Stream.of(
                new Object[]{HttpStatus.NOT_FOUND, "not found available drone BUSY"},
                new Object[]{HttpStatus.OK, "Ok"},
                new Object[]{HttpStatus.NOT_FOUND, "not found available drone BATTERY LOW"},
                new Object[]{HttpStatus.NOT_FOUND, "not found medication"},
                new Object[]{HttpStatus.BAD_REQUEST, "weight limit exceeded"},
                new Object[]{HttpStatus.BAD_REQUEST, "empty serial number"},
                new Object[]{HttpStatus.BAD_REQUEST, "empty medications"}
        );
    }
}
