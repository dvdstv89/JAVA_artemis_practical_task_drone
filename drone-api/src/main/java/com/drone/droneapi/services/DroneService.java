package com.drone.droneapi.services;

import com.drone.droneapi.config.MessageText;
import com.drone.droneapi.dto.*;
import com.drone.droneapi.models.Drone;
import com.drone.droneapi.models.DroneMedication;
import com.drone.droneapi.models.Medication;
import com.drone.droneapi.models.enums.DroneState;
import com.drone.droneapi.repositories.DroneRepository;
import com.drone.droneapi.repositories.MedicationRepository;
import com.drone.droneapi.utils.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DroneService extends BaseService implements IDroneService {

    private final ModelMapper modelMapper;
    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;

    @Autowired
    public DroneService(DroneRepository droneRepository, MedicationRepository medicationRepository, ModelMapper modelMapper) {
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse createDrone(DroneDto droneDto) {
        try {
            Drone drone = getDrone(droneDto.getSerialNumber());
            if (drone != null) {
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
    public ApiResponse loadMedicationsIntoDrone(String serialNumber, List<DroneMedicationDto> droneMedicationDto) {
        try {
            if (droneMedicationDto == null) {
                throw new Exception(MessageText.MEDICATIONS_EMPTY);
            }
            Drone drone = getDroneAvailableForLoading(serialNumber);
            if (drone == null) {
                return response;
            }
            Double totalWeight = 0.0;

            for (DroneMedicationDto medicationDto : droneMedicationDto)
            {
                Optional<Medication> medicationOptional = medicationRepository.findById(medicationDto.getCode());
                if (medicationOptional.isEmpty())
                {
                    response.addNotFoundResponse404(String.format(MessageText.MEDICATION_NOT_FOUND, medicationDto.getCode()));
                    return response;
                }

                DroneMedication droneMedicationExitent = drone.getDroneMedications()
                        .stream()
                        .filter(dr -> dr.getMedication().getCode().equals(medicationDto.getCode()))
                        .findFirst()
                        .orElse(null);

                if (droneMedicationExitent != null)
                {
                    droneMedicationExitent.setCount(droneMedicationExitent.getCount() + medicationDto.getCount());
                }
                else
                {
                    DroneMedication droneMedication = new DroneMedication(medicationDto.getCount(),drone,medicationOptional.get());
                    drone.getDroneMedications().add(droneMedication);
                }

                totalWeight += medicationOptional.get().getWeight() * medicationDto.getCount();
            }

            if (drone.getWeightLimit() < totalWeight)
            {
                throw new Exception(String.format(MessageText.DRONE_CARGO_WEIGHT_EXCEEDED, drone.getWeightLimit(), totalWeight));
            }
            drone.setState(DroneState.LOADED);
            droneRepository.save(drone);
            response.addOkResponse200(MessageText.DRONE_LOADED);

        } catch (Exception ex) {
            response.addBadRequestResponse400(ex.getMessage());
        }
        return response;
    }

    @Override
    public ApiResponse checkLoadedMedicationsIntoDrone(String serialNumber) {
        try {
            Drone drone = getDrone(serialNumber);
            if (drone != null) {
                List<DroneMedicationCheckOutputDto> responseDto = drone.getDroneMedications().stream()
                        .map(dm -> modelMapper.map(dm, DroneMedicationCheckOutputDto.class))
                        .collect(Collectors.toList());
                if (responseDto.isEmpty()) {
                    response.addNotFoundResponse404(String.format(MessageText.MEDICATION_LOADED_NOT_FOUND, serialNumber));
                } else {
                    response.addOkResponse200(responseDto);
                }
            } else {
                response.addNotFoundResponse404(String.format(MessageText.DRONE_NOT_FOUND, serialNumber));
            }
        } catch (Exception ex) {
            response.addBadRequestResponse400(ex.getMessage());
        }
        return response;
    }

    @Override
    public ApiResponse getDronesAvailablesForLoading() {
        try {
            List<Drone> drone = getDroneAvailablesForLoading();
            if (!drone.isEmpty()) {
                List<DroneDto> responseDto = drone.stream()
                        .map(d -> modelMapper.map(d, DroneDto.class))
                        .collect(Collectors.toList());
                response.addOkResponse200(responseDto);
            } else {
                response.addNotFoundResponse404(MessageText.DRONE_NOT_FOUND_AVAILABLE_FOR_LOADING);
            }
        } catch (Exception ex) {
            response.addBadRequestResponse400(ex.getMessage());
        }
        return response;
    }

    @Override
    public ApiResponse changeDroneState(String serialNumber, DroneStateOutputDto droneStateDto) {
        try {
            Drone drone = getDrone(serialNumber);
            if (drone != null) {
                if (drone.getBatteryCapacity() < 25 && droneStateDto.getState() == DroneState.LOADING) {
                    response.addBadRequestResponse400(String.format(MessageText.DRONE_CHANGE_STATE_TO_LOADING_WITH_BATTERY_LOW, serialNumber, drone.getBatteryCapacity()));
                } else {
                    drone.setState(droneStateDto.getState());
                    droneRepository.save(drone);
                    DroneDto responseDto = modelMapper.map(drone, DroneDto.class);
                    response.addOkResponse200(responseDto);
                }
            } else {
                response.addNotFoundResponse404(String.format(MessageText.DRONE_NOT_FOUND, serialNumber));
            }
        } catch (Exception ex) {
            response.addBadRequestResponse400(ex.getMessage());
        }
        return response;
    }

    @Override
    public ApiResponse checkBatteryCapacity(String serialNumber) {
        try {
            Drone drone = getDrone(serialNumber);
            if (drone != null) {
                DroneBatteryLevelDto responseDto = modelMapper.map(drone, DroneBatteryLevelDto.class);
                response.addOkResponse200(responseDto);
            } else {
                response.addNotFoundResponse404(String.format(MessageText.DRONE_NOT_FOUND, serialNumber));
            }
        } catch (Exception ex) {
            response.addBadRequestResponse400(ex.getMessage());
        }
        return response;
    }

    @Override
    public ApiResponse changeBatteryCapacity(String serialNumber, DroneBatteryLevelDto droneInput) {
        try {
            Drone drone = getDrone(serialNumber);
            if (drone != null) {
                drone.setBatteryCapacity(droneInput.getBatteryCapacity());
                droneRepository.save(drone);
                DroneDto responseDto = modelMapper.map(drone, DroneDto.class);
                response.addOkResponse200(responseDto);
            } else {
                response.addNotFoundResponse404(String.format(MessageText.DRONE_NOT_FOUND, serialNumber));
            }
        } catch (Exception ex) {
            response.addBadRequestResponse400(ex.getMessage());
        }
        return response;
    }

    @Override
    public ApiResponse getDronebySerialNumbre(String serialNumber) {
        try {
            Drone drone = getDrone(serialNumber);
            if (drone != null) {
                DroneDto responseDto = modelMapper.map(drone, DroneDto.class);
                response.addOkResponse200(responseDto);
            } else {
                response.addNotFoundResponse404(String.format(MessageText.DRONE_NOT_FOUND, serialNumber));
            }
        } catch (Exception ex) {
            response.addBadRequestResponse400(ex.getMessage());
        }
        return response;
    }

    @Override
    public ApiResponse deleteDrone(String serialNumber) {
        try {
            Drone drone = getDrone(serialNumber);
            if(drone != null){
                droneRepository.deleteById(serialNumber);
                response.addOkResponse200("OK");
            }
            else{
                response.addNotFoundResponse404(String.format(MessageText.DRONE_NOT_FOUND, serialNumber));
            }
        } catch (Exception ex) {
            response.addNotFoundResponse404(String.format(MessageText.DRONE_NOT_FOUND, serialNumber));
        }
        return response;
    }

    @Override
    public ApiResponse getDrones() {
        try {
            List<DroneDto> responseDtos = droneRepository.findAll()
                    .stream()
                    .map(e -> modelMapper.map(e, DroneDto.class))
                    .collect(Collectors.toList());
            response.addOkResponse200(responseDtos);
        } catch (Exception ex) {
            response.addBadRequestResponse400(ex.getMessage());
        }
        return response;
    }

    private Drone getDrone(String serialNumber) {
        return droneRepository.findById(serialNumber).orElse(null);
    }

    private List<Drone> getDroneAvailablesForLoading() {
        return droneRepository.findDroneAvailablesForLoading();
    }

    private Drone getDroneAvailableForLoading(String serialNumber)  {
        try
        {
            Drone drone = getDrone(serialNumber);
            if (drone == null)
            {
                response.addNotFoundResponse404(String.format(MessageText.DRONE_NOT_FOUND, serialNumber));
            }
            else{
                if (drone.getState() != DroneState.IDLE)
                {
                    response.addNotFoundResponse404(String.format(MessageText.DRONE_STATE_NO_READY_TO_FLY_BUSY, serialNumber, drone.getState()));
                    return null;
                }
                if (drone.getBatteryCapacity() < 25)
                {
                    response.addNotFoundResponse404(String.format(MessageText.DRONE_STATE_NO_READY_TO_FLY_BATTERY_LOW, serialNumber, drone.getBatteryCapacity()));
                    return null;
                }
            }
            return drone;
        }
        catch (Exception ex)
        {
           response.addBadRequestResponse400(ex.getMessage());
           return null;
        }
    }
}
