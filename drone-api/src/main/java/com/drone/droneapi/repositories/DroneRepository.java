package com.drone.droneapi.repositories;

import com.drone.droneapi.models.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone,String> {
    @Query("SELECT d FROM Drone d WHERE d.state = 'IDLE' AND d.batteryCapacity > 25")
    List<Drone> findDroneAvailablesForLoading();
}
