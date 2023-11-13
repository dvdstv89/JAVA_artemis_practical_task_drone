package com.drone.droneapi.dto;

import com.drone.droneapi.models.enums.DroneModel;
import com.drone.droneapi.models.enums.DroneState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class DroneDto {

    @NotBlank(message = "El número de serie no puede estar en blanco")
    private String serialNumber;

    @NotNull(message = "El modelo no puede ser nulo")
    private DroneModel model;

    @NotNull(message = "El límite de peso no puede ser nulo")
    @Range(min = 1, max = 500, message = "El límite de peso debe estar entre 1 y 500")
    private Double weightLimit;

    @NotNull(message = "La capacidad de la batería no puede ser nula")
    @Range(min = 0, max = 100, message = "La capacidad de la batería debe estar entre 0 y 100")
    private Double batteryCapacity;

    @NotNull(message = "El estado no puede ser nulo")
    private DroneState state;
}
