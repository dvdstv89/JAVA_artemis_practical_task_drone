package com.drone.droneapi.models.enums;

public enum DroneModel {
    Lightweight(1),
    Middleweight(2),
    Cruiserweight(3),
    Heavyweight(4);

    private final int value;

    DroneModel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
