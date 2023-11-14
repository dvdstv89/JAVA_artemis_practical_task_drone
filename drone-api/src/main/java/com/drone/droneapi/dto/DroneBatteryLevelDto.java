package com.drone.droneapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class DroneBatteryLevelDto {
    @NotNull(message = "La capacidad de la batería no puede ser nula")
    @Range(min = 0, max = 100, message = "La capacidad de la batería debe estar entre 0 y 100")
    private Double batteryCapacity;
}
