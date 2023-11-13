package com.drone.droneapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.util.UUID;

@Entity
@Table
@Getter
@Setter
public class DroneMedication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Range(min = 1, max = 100)
    private int count;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "drone_serial_number")
    private Drone drone;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "medication_code")
    private Medication medication;

    public DroneMedication(int count, Drone drone, Medication medication) {
        this.count = count;
        this.drone = drone;
        this.medication = medication;
    }

    public DroneMedication() {
    }
}