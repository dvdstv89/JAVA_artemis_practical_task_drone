package com.drone.droneapi.models;

import com.drone.droneapi.config.MessageText;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Medication {

    @Id
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

    @JsonManagedReference
    @OneToMany(mappedBy = "medication", cascade = CascadeType.ALL)
    private List<DroneMedication> droneMedications;

    public Medication(String code, String name, double weight, byte[] image) {
        this.code = code;
        this.name = name;
        this.weight = weight;
        this.image = image;
        droneMedications = new ArrayList<>();
    }

    public Medication(String code) {
        this.code = code;
        droneMedications = new ArrayList<>();
    }

    public Medication() {
        droneMedications = new ArrayList<>();
    }
}
