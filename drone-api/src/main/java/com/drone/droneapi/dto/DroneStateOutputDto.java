package com.drone.droneapi.dto;

import com.drone.droneapi.models.enums.DroneState;
import lombok.Data;

@Data
public class DroneStateOutputDto {
    private DroneState state;
}
