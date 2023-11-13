package com.drone.droneapi.config;

import com.drone.droneapi.models.enums.DroneModel;
import com.drone.droneapi.models.enums.DroneState;
import com.drone.droneapi.models.Drone;
import com.drone.droneapi.models.DroneMedication;
import com.drone.droneapi.models.Medication;
import com.drone.droneapi.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class InitDataConfig {

    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;
    private final DroneMedicationRepository droneMedicationRepository;
    @Autowired
    public InitDataConfig(DroneRepository droneRepository, MedicationRepository medicationRepository, DroneMedicationRepository droneMedicationRepository) {
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
        this.droneMedicationRepository = droneMedicationRepository;
    }


    @Bean
    public CommandLineRunner initData() {
        return args -> {
            List<Drone> drones = createSampleDrones();
            List<Medication> medications = createSampleMedications();
            List<DroneMedication> droneMedications = createSampleDroneMedications();

            droneRepository.saveAll(drones);
            medicationRepository.saveAll(medications);
            droneMedicationRepository.saveAll(droneMedications);
        };
    }
    private List<Drone> createSampleDrones() {
        return List.of(
                new Drone("DRN001", DroneModel.Lightweight, 100.0, 80.0, DroneState.IDLE),
                new Drone("DRN002", DroneModel.Lightweight, 175.0, 70.0, DroneState.LOADED),
                new Drone("DRN003", DroneModel.Middleweight, 250.0, 18.0, DroneState.RETURNING),
                new Drone("DRN004", DroneModel.Middleweight, 300.0, 23.0, DroneState.RETURNING),
                new Drone("DRN005", DroneModel.Cruiserweight, 350.0, 40.0, DroneState.IDLE),
                new Drone("DRN006", DroneModel.Cruiserweight, 400.0, 5.0, DroneState.IDLE),
                new Drone("DRN007", DroneModel.Cruiserweight, 325.0, 75.0, DroneState.IDLE ),
                new Drone("DRN008", DroneModel.Heavyweight, 500.0, 80.0, DroneState.IDLE ),
                new Drone("DRN009", DroneModel.Heavyweight, 480.0, 98.0, DroneState.IDLE ),
                new Drone("DRN010", DroneModel.Heavyweight, 500.0, 100.0, DroneState.IDLE)
        );
    }

    private List<Medication> createSampleMedications() {
        return List.of(
                new Medication("MED001", "MedicationA", 80.0, new byte[] { (byte) 255, (byte) 255, (byte) 255, (byte) 255 }),
                new Medication("MED002", "MedicationB", 60.0, new byte[] { (byte) 255, (byte) 255, (byte) 255, (byte) 255 }),
                new Medication("MED003", "MedicationC", 45.0, new byte[] { (byte) 255, (byte) 255, (byte) 255, (byte) 255 }),
                new Medication("MED004", "MedicationD", 97.0, new byte[] { (byte) 255, (byte) 255, (byte) 255, (byte) 255 }),
                new Medication("MED005", "MedicationE", 38.0, new byte[] { (byte) 255, (byte) 255, (byte) 255, (byte) 255 }),
                new Medication("MED006", "MedicationF", 100.0, new byte[] { (byte) 255, (byte) 255, (byte) 255, (byte) 255 }),
                new Medication("MED007", "MedicationG", 50.0, new byte[] { (byte) 255, (byte) 255, (byte) 255, (byte) 255 }),
                new Medication("MED008", "MedicationH", 75.0, new byte[] { (byte) 255, (byte) 255, (byte) 255, (byte) 255 }),
                new Medication("MED009", "MedicationI", 57.0, new byte[] { (byte) 255, (byte) 255, (byte) 255, (byte) 255 }),
                new Medication("MED010", "MedicationJ", 40.0, new byte[] { (byte) 255, (byte) 255, (byte) 255, (byte) 255 })
        );
    }

    private List<DroneMedication> createSampleDroneMedications() {
        return List.of(
                new DroneMedication(1, new Drone("DRN002"),new Medication("MED001") ),
                new DroneMedication(1, new Drone("DRN002"),new Medication("MED002") )
        );
    }

}
