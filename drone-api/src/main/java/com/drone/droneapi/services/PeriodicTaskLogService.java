package com.drone.droneapi.services;

import com.drone.droneapi.config.MessageText;
import com.drone.droneapi.models.Drone;
import com.drone.droneapi.models.PeriodicTaskLog;
import com.drone.droneapi.repositories.PeriodicTaskLogRepository;
import com.drone.droneapi.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PeriodicTaskLogService extends BaseService implements IPeriodicTaskLogService {

    private final PeriodicTaskLogRepository periodicTaskLogRepository;
    private final IDroneService droneService;

    @Autowired
    public PeriodicTaskLogService(PeriodicTaskLogRepository periodicTaskLogRepository, IDroneService droneService) {
        this.periodicTaskLogRepository = periodicTaskLogRepository;
        this.droneService = droneService;
    }

    public ApiResponse getAllLogsBySerialNumber(String serialNumber) {
        try {
            if (isNullOrEmpty(serialNumber)) {
                throw new IllegalArgumentException(MessageText.DRONE_SERIAL_NUMBER_EMPTY);
            }
            List<PeriodicTaskLog> logs = periodicTaskLogRepository.findBySerialNumber(serialNumber);
            if (isNullOrEmpty(logs)) {
                response.addNotFoundResponse404(String.format(MessageText.DRONE_LOG_NOT_FOUND, serialNumber));
            } else {
                response.addOkResponse200(logs);
            }
        } catch (Exception ex) {
            response.addBadRequestResponse400(ex.getMessage());
        }
        return response;
    }

    public ApiResponse periodicTaskLogs() {
        try {
            ApiResponse droneApiResponse = droneService.getDrones();
            if (droneApiResponse.isValid()) {
                var drones = (List<Drone>) droneApiResponse.getResult();
                if (isNullOrEmpty(drones)) {
                    throw new IllegalArgumentException(MessageText.DRONE_NOT_FOUND_EMPTY_DB);
                } else {
                    return createLogs(drones);
                }
            }
            return droneApiResponse;
        } catch (Exception ex) {
            response.addBadRequestResponse400(ex.getMessage());
        }
        return response;
    }

    private ApiResponse createLogs(List<Drone> drones) {
        try {
            List<String> messages = new ArrayList<>();
            drones.forEach(drone -> {
                createLog(drone);
                messages.add(response.isValid()
                        ? (String) response.getResult()
                        : String.join(" && ", response.getErrors()));
            });
            String logMessage = "\n" + String.join("\n", messages);
            response.addOkResponse200(logMessage);
        } catch (Exception ex) {
            response.addBadRequestResponse400(ex.getMessage());
        }
        return response;
    }

    private void createLog(Drone drone) {
        try {
            PeriodicTaskLog droneBatteryLog = new PeriodicTaskLog(drone.getSerialNumber(), drone.getBatteryCapacity());
            periodicTaskLogRepository.save(droneBatteryLog);
            response.addOkResponse200(droneBatteryLog.toString());
        } catch (Exception ex) {
            response.addBadRequestResponse400(ex.getMessage());
        }
    }
}
