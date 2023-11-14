package com.drone.droneapi.dto;

import lombok.Data;

@Data
public class DroneMedicationCheckOutputDto {
    private int count;
    private MedicationDto medication;
}
