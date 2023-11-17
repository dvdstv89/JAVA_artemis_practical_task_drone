package com.drone.droneapi.services;

import com.drone.droneapi.error.ApiResponse;
import com.drone.droneapi.error.LocalBadRequestException;
import com.drone.droneapi.models.Drone;
import com.drone.droneapi.models.enums.DroneModel;
import com.drone.droneapi.models.enums.DroneState;
import com.drone.droneapi.repositories.DroneRepository;
import com.drone.droneapi.repositories.MedicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class DroneServiceTest {
    @Mock
    protected ApiResponse response;
    @Mock
    private DroneRepository droneRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private MedicationRepository medicationRepository;
    @InjectMocks
    private DroneService droneService;

    private Drone drone;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        drone = new Drone("DRN001", DroneModel.Lightweight, 100.0, 80.0, DroneState.IDLE);

    }

    @Test
    void createDrone() {
    }

    @Test
    void loadMedicationsIntoDrone() {
    }

    @Test
    void checkLoadedMedicationsIntoDrone() {
    }

    @Test
    void getDronesAvailablesForLoading() {
    }

    @Test
    void changeDroneState() {
    }

    @Test
    void checkBatteryCapacity() {
    }

    @Test
    void changeBatteryCapacity() {
    }

    @Test
    void getDronebySerialNumbre() {
    }

    @Test
    void deleteDrone() {
    }

    @ParameterizedTest(name = "Load medication into drone with {0}: {1}")
    @MethodSource("com.drone.droneapi.services.DroneServiseTestSuport#getDronesTestCases")
    void getDrones(HttpStatus expectedHttpStatus, String testName) throws LocalBadRequestException {
        when(droneRepository.findAll()).thenReturn(Arrays.asList(drone));
        assertNotNull(droneService.getDrones());
    }
}