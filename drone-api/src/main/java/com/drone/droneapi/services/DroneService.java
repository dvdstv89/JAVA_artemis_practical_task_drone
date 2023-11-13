package com.drone.droneapi.services;

import com.drone.droneapi.config.MessageText;
import com.drone.droneapi.dto.DroneDto;
import com.drone.droneapi.models.Drone;
import com.drone.droneapi.repositories.DroneRepository;
import com.drone.droneapi.utils.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DroneService extends BaseService implements IDroneService {

    private final ModelMapper modelMapper;
    private final DroneRepository droneRepository;

    @Autowired
    public DroneService(DroneRepository droneRepository, ModelMapper modelMapper) {
        this.droneRepository = droneRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse createDrone(DroneDto droneDto) {
        try {
            getDrone(droneDto.getSerialNumber());
            if (response.isValid()) {
                throw new IllegalArgumentException(String.format(MessageText.DRONE_SERIAL_NUMBER_DUPLICATED, droneDto.getSerialNumber()));
            }
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                Drone newDrone = modelMapper.map(droneDto, Drone.class);
                Drone savedDrone = droneRepository.save(newDrone);
                DroneDto responseDto = modelMapper.map(savedDrone, DroneDto.class);
                response.addCreateResponse201(responseDto);
                return response;
            }
        } catch (Exception ex) {
            response.addBadRequestResponse400(ex.getMessage());
        }
        return response;
    }

    @Override
    public ApiResponse getDrone(String serialNumber) {
        try {
            Optional<Drone> optionalDrone  = droneRepository.findById(serialNumber);
            if (optionalDrone.isPresent()) {
                Drone drone = optionalDrone.get();
                response.addOkResponse200(drone);
            } else {
                response.addNotFoundResponse404(String.format(MessageText.DRONE_NOT_FOUND, serialNumber));
            }
        } catch (Exception ex) {
            response.addNotFoundResponse404(ex.getMessage());
        }
        return response;
    }

    @Override
    public ApiResponse updateDrone(String serialNumber, Drone updatedDrone) {
        ApiResponse existingDrone = getDrone(serialNumber);
        if (existingDrone.isValid()) {
            Drone drone = (Drone) existingDrone.getResult();
            drone.setState(updatedDrone.getState());
        }
        return response;
    }

    @Override
    public ApiResponse deleteDrone(String serialNumber) {
        try {
            droneRepository.deleteById(serialNumber);
            response.addOkResponse200("OK");
        } catch (Exception ex) {
            response.addNotFoundResponse404("NO FOUND");
        }
        return response;
    }

    @Override
    public ApiResponse getDrones() {
        try {
            List<Drone> drones = droneRepository.findAll();
            response.addOkResponse200(drones);
        } catch (Exception ex) {
            response.addBadRequestResponse400(ex.getMessage());
        }
        return response;
    }
}
