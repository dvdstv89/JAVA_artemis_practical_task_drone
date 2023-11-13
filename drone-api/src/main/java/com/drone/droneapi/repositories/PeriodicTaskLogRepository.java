package com.drone.droneapi.repositories;

import com.drone.droneapi.models.PeriodicTaskLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodicTaskLogRepository extends JpaRepository<PeriodicTaskLog,String> {
    List<PeriodicTaskLog> findBySerialNumber(String serialNumber);
}
