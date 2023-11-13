package com.drone.droneapi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Table
@Data
public class PeriodicTaskLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private String serialNumber;

    @NotNull
    private double batteryCapacity;

    @NotNull
    private Date date;

    public PeriodicTaskLog(String serialNumber, double batteryCapacity) {
        this.serialNumber = serialNumber;
        this.batteryCapacity = batteryCapacity;
        date = new Date();
    }

    public  PeriodicTaskLog (){ date = new Date();}

    @Override
    public String toString() {
        return String.format("Drone: %-10s | BatteryCapacity: %-5s | Date: %-25s", serialNumber, batteryCapacity, date);
    }
}
