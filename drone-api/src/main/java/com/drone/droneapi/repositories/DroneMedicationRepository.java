package com.drone.droneapi.repositories;

import com.drone.droneapi.models.DroneMedication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneMedicationRepository extends JpaRepository<DroneMedication,String> {
}
