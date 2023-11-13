package com.drone.droneapi.models.enums;

public enum DroneState {
    IDLE(1),
    LOADING(2),
    LOADED(3),
    DELIVERING(4),
    DELIVERED(5),
    RETURNING(6);

    private final int value;

    DroneState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
