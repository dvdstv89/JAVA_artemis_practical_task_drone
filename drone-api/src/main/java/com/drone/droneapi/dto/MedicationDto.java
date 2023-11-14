package com.drone.droneapi.dto;

import com.drone.droneapi.config.MessageText;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class MedicationDto {

    @Pattern(regexp = "^[A-Z0-9_]+$", message = MessageText.MEDICATION_CODE_FORMAT_VALIDATION)
    private String code;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = MessageText.MEDICATION_NAME_FORMAT_VALIDATION)
    private String name;

    @NotNull
    @Range(min = 1, max = 500, message = MessageText.MEDICATION_WEIGHT_MIN_VALUE_VALIDATION)
    private double weight;

    @Lob
    private byte[] image;
}
