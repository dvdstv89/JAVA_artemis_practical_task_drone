package com.drone.droneapi.dto;

import com.drone.droneapi.config.MessageText;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class DroneMedicationDto {
    @Pattern(regexp = "^[A-Z0-9_]+$", message = MessageText.MEDICATION_CODE_FORMAT_VALIDATION)
    private String code;

    @NotNull
    @Range(min = 1, max = 100)
    private int count;
}
