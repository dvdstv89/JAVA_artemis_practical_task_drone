package com.drone.droneapi.repositories;

import com.drone.droneapi.models.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneRepository extends JpaRepository<Drone,String> {
}
