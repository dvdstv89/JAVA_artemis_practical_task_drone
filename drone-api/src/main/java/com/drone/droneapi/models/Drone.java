package com.drone.droneapi.models;

import com.drone.droneapi.models.enums.DroneModel;
import com.drone.droneapi.models.enums.DroneState;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Drone {
    @Id
    @Column(length = 100)
    private String serialNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DroneModel model;

    @Column(nullable = false)
    @Range(min = 1, max = 500)
    private Double weightLimit;

    @Column(nullable = false)
    @Range(min = 0, max = 100)
    private Double batteryCapacity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DroneState state;

    @JsonManagedReference
    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL)
    private List<DroneMedication> droneMedications;

    public Drone() {
        droneMedications = new ArrayList<>();
    }
    public Drone(String serialNumber, DroneModel model, Double weightLimit, Double batteryCapacity, DroneState state) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.weightLimit = weightLimit;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
        droneMedications = new ArrayList<>();
    }

    public Drone(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
