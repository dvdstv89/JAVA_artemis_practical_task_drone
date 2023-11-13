package com.drone.droneapi.repositories;

import com.drone.droneapi.models.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends JpaRepository<Medication,String> {
}
