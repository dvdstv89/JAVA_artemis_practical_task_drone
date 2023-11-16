package com.drone.droneapi.error;

public class MessageText {
    public static final String DRONE_CARGO_WEIGHT_EXCEEDED = "The maximum load weight of the drone %sgr was exceeded. Load weight to transport of %sgr";
    public static final String DRONE_CHANGE_STATE_TO_LOADING_WITH_BATTERY_LOW = "The drone SN:%s has the battery status low %.2f%%";
    public static final String DRONE_LOADED = "The drone was loaded with the medication satisfactorily.";
    public static final String DRONE_LOG_NOT_FOUND = "No battery capacity records found for drone with this serial number %s";
    public static final String DRONE_NOT_FOUND = "No drone found with the serial number %s";
    public static final String DRONE_NOT_FOUND_EMPTY_DB = "No drone founds in the database";
    public static final String DRONE_NOT_FOUND_AVAILABLE_FOR_LOADING = "No drone found available for loading";
    public static final String DRONE_SERIAL_NUMBER_DUPLICATED = "Exist a drone registered with the same serial number %s";
    public static final String DRONE_SERIAL_NUMBER_EMPTY = "The serial number of the drone cannot be empty";
    public static final String DRONE_STATE_NO_READY_TO_FLY_BATTERY_LOW = "The drone SN:%s is not available because the battery status is low %s%";
    public static final String DRONE_STATE_NO_READY_TO_FLY_BUSY = "The drone SN:%s is not ready to available because is busy. Status %s";

    public static final String MEDICATION_CODE_FORMAT_VALIDATION = "Code must only contain upper case letters, numbers, or '_'.";
    public static final String MEDICATION_NAME_FORMAT_VALIDATION = "Name must only contain letters, numbers, '_', or '-'.";
    public static final String MEDICATIONS_EMPTY = "No medications provided for loading";
    public static final String MEDICATION_NOT_FOUND = "No medication found with the Code %s";
    public static final String MEDICATION_LOADED_NOT_FOUND = "No medication loaded found in the drone with serial number %s";
    public static final String MEDICATION_WEIGHT_MIN_VALUE_VALIDATION = "Weight must be greater than 0.";

    public static final String ENDPOINT_NAME_REGISTER_DRONE = "Registering a new drone";
    public static final String ENDPOINT_NAME_LOAD_MEDICATION = "Loading a drone with medication items";
    public static final String ENDPOINT_NAME_GET_DRONE = "Get a drone filter by your serial number";
    public static final String ENDPOINT_NAME_GET_ALL_DRONE = "Get all drones";
    public static final String ENDPOINT_NAME_DELETE_DRONE = "Delete drone";
    public static final String ENDPOINT_NAME_CHECK_LOAD_MEDICATION = "Checking loaded medication items for a given drone";
    public static final String ENDPOINT_NAME_CHECK_AVAILABLE_DRONES = "Checking available drones for loading";
    public static final String ENDPOINT_NAME_CHECK_BATTERY_LEVEL_DRONE = "Check drone battery level for a given drone";
    public static final String ENDPOINT_NAME_Change_STATE_DRONE = "Change drone state for a given drone";
    public static final String ENDPOINT_NAME_Change_BATTERY_LEVEL_DRONE = "Change drone battery capacity for a given drone";
    public static final String ENDPOINT_NAME_SHOW_LOGS = "Check drone battery capacity logs for a given drone";

    public static final String HANDLE_API_RESPONSE_OK = "Request processed successfully from endpoint => %s";
    public static final String HANDLE_API_RESPONSE_CREATED = "Resource created successfully from endpoint => %s";
    public static final String HANDLE_API_RESPONSE_NOT_FOUND = "Resource not found from endpoint => %s";
    public static final String HANDLE_API_RESPONSE_BAD_RESPONSE = "Bad request received from endpoint =>  %s";

    public static final String HANDLE_API_RESPONSE_VALIDATION_ERROR = "Validation error =>  %s";

    public static final String PERIODIC_TASK_EXCEPTION = " Failed to execute PeriodicHostedService with exception message %s";
}
