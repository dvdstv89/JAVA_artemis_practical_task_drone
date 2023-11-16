package com.drone.droneapi.services;

import com.drone.droneapi.dto.DroneDto;
import com.drone.droneapi.error.*;
import com.drone.droneapi.models.PeriodicTaskLog;
import com.drone.droneapi.repositories.PeriodicTaskLogRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeriodicTaskLogService extends BaseService implements IPeriodicTaskLogService {

    private final PeriodicTaskLogRepository periodicTaskLogRepository;
    private final IDroneService droneService;

    @Autowired
    public PeriodicTaskLogService(PeriodicTaskLogRepository periodicTaskLogRepository, IDroneService droneService) {
        this.periodicTaskLogRepository = periodicTaskLogRepository;
        this.droneService = droneService;
    }

    public ApiResponse getAllLogsBySerialNumber(String serialNumber) throws LocalBadRequestException, LocalIlegalArgumentRequestException, LocalNotFoundException {
        try {
            if (isNullOrEmpty(serialNumber)) {
                throw new LocalIlegalArgumentRequestException(MessageText.DRONE_SERIAL_NUMBER_EMPTY);
            }

            List<PeriodicTaskLog> logs = periodicTaskLogRepository.findBySerialNumber(serialNumber);
            if (isNullOrEmpty(logs)) {
                throw new LocalNotFoundException(String.format(MessageText.DRONE_LOG_NOT_FOUND, serialNumber));
            }
            response.addOkResponse200(logs);
            return response;
        }
        catch (LocalIlegalArgumentRequestException | LocalNotFoundException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new LocalBadRequestException(ex.getMessage());
        }
    }

    public ApiResponse periodicTaskLogs() throws LocalBadRequestException, LocalNotFoundException {
        try {
            ApiResponse droneApiResponse = droneService.getDrones();
            if (droneApiResponse.isValid()) {
                List<DroneDto> drones = (List<DroneDto>) droneApiResponse.getResult();
                if (isNullOrEmpty(drones)) {
                    throw new LocalNotFoundException(MessageText.DRONE_NOT_FOUND_EMPTY_DB);
                }
                return createLogs(drones);
            }
            return droneApiResponse;
        } catch (LocalNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new LocalBadRequestException(ex.getMessage());
        }
    }

    @SneakyThrows
    private ApiResponse createLogs(List<DroneDto> drones) {
        try {
            String logMessage = drones.stream()
                    .peek(this::createLog)
                    .map(drone -> response.isValid() ? (String) response.getResult() : String.join(" && ", response.getErrors()))
                    .collect(Collectors.joining("\n", "\n", ""));
            response.addOkResponse200(logMessage);
            return response;
        } catch (Exception ex) {
            throw new LocalBadRequestException(ex.getMessage());
        }
    }

    @SneakyThrows
    private void createLog(DroneDto drone) {
        try {
            PeriodicTaskLog droneBatteryLog = new PeriodicTaskLog(drone.getSerialNumber(), drone.getBatteryCapacity());
            periodicTaskLogRepository.save(droneBatteryLog);
            response.addOkResponse200(droneBatteryLog.toString());
        } catch (Exception ex) {
            throw new LocalBadRequestException(ex.getMessage());
        }
    }
}
