package com.drone.droneapi.services;

import com.drone.droneapi.dto.*;
import com.drone.droneapi.error.*;
import com.drone.droneapi.models.Drone;
import com.drone.droneapi.models.DroneMedication;
import com.drone.droneapi.models.Medication;
import com.drone.droneapi.models.enums.DroneState;
import com.drone.droneapi.repositories.DroneRepository;
import com.drone.droneapi.repositories.MedicationRepository;
import lombok.SneakyThrows;
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
    public ApiResponse createDrone(DroneDto droneDto) throws LocalBadRequestException {
        try {
            try {
                getDrone(droneDto.getSerialNumber());
            } catch (Exception ex) {
                Drone newDrone = modelMapper.map(droneDto, Drone.class);
                Drone savedDrone = droneRepository.save(newDrone);
                DroneDto responseDto = modelMapper.map(savedDrone, DroneDto.class);
                response.addCreateResponse201(responseDto);
                return response;
            }
            throw new LocalBadRequestException(String.format(MessageText.DRONE_SERIAL_NUMBER_DUPLICATED, droneDto.getSerialNumber()));
        } catch (Exception ex) {
            throw new LocalBadRequestException(ex.getMessage());
        }
    }

    @Override
    public ApiResponse loadMedicationsIntoDrone(String serialNumber, List<DroneMedicationDto> droneMedicationDto) throws LocalNotFoundException, LocalBadRequestException, LocalIlegalArgumentRequestException {
        try {
            if (droneMedicationDto == null) {
                throw new LocalIlegalArgumentRequestException(MessageText.MEDICATIONS_EMPTY);
            }
            Drone drone = getDroneAvailableForLoading(serialNumber);

            Double totalWeight = 0.0;
            for (DroneMedicationDto medicationDto : droneMedicationDto) {
                Optional<Medication> medicationOptional = medicationRepository.findById(medicationDto.getCode());
                if (medicationOptional.isEmpty()) {
                    throw new LocalNotFoundException(String.format(MessageText.MEDICATION_NOT_FOUND, medicationDto.getCode()));
                }

                DroneMedication droneMedicationExitent = drone.getDroneMedications()
                        .stream()
                        .filter(dr -> dr.getMedication().getCode().equals(medicationDto.getCode()))
                        .findFirst()
                        .orElse(null);

                if (droneMedicationExitent != null) {
                    droneMedicationExitent.setCount(droneMedicationExitent.getCount() + medicationDto.getCount());
                } else {
                    DroneMedication droneMedication = new DroneMedication(medicationDto.getCount(), drone, medicationOptional.get());
                    drone.getDroneMedications().add(droneMedication);
                }

                totalWeight += medicationOptional.get().getWeight() * medicationDto.getCount();
            }

            if (drone.getWeightLimit() < totalWeight) {
                throw new Exception(String.format(MessageText.DRONE_CARGO_WEIGHT_EXCEEDED, drone.getWeightLimit(), totalWeight));
            }
            drone.setState(DroneState.LOADED);
            droneRepository.save(drone);
            response.addOkResponse200(MessageText.DRONE_LOADED);
            return response;

        } catch (LocalNotFoundException | LocalIlegalArgumentRequestException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new LocalBadRequestException(ex.getMessage());
        }
    }

    @Override
    public ApiResponse checkLoadedMedicationsIntoDrone(String serialNumber) throws
            LocalBadRequestException, LocalNotFoundException {
        try {
            Drone drone = getDrone(serialNumber);
            List<DroneMedicationCheckOutputDto> responseDto = drone.getDroneMedications().stream()
                    .map(dm -> modelMapper.map(dm, DroneMedicationCheckOutputDto.class))
                    .collect(Collectors.toList());
            if (responseDto.isEmpty()) {
                throw new LocalNotFoundException(String.format(MessageText.MEDICATION_LOADED_NOT_FOUND, serialNumber));
            }
            response.addOkResponse200(responseDto);
            return response;
        } catch (LocalNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new LocalBadRequestException(ex.getMessage());
        }
    }

    @Override
    public ApiResponse getDronesAvailablesForLoading() throws LocalNotFoundException, LocalBadRequestException {
        try {
            List<Drone> drone = getDroneAvailablesForLoading();
            if (drone.isEmpty()) {
                throw new LocalNotFoundException(MessageText.DRONE_NOT_FOUND_AVAILABLE_FOR_LOADING);
            }
            List<DroneDto> responseDto = drone.stream()
                    .map(d -> modelMapper.map(d, DroneDto.class))
                    .collect(Collectors.toList());
            response.addOkResponse200(responseDto);
            return response;
        } catch (LocalNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new LocalBadRequestException(ex.getMessage());
        }
    }

    @Override
    public ApiResponse changeDroneState(String serialNumber, DroneStateOutputDto droneStateDto) throws
            LocalNotFoundException, LocalBadRequestException {
        try {
            Drone drone = getDrone(serialNumber);
            if (drone.getBatteryCapacity() < 25 && droneStateDto.getState() == DroneState.LOADING) {
                throw new LocalNotFoundException(String.format(MessageText.DRONE_CHANGE_STATE_TO_LOADING_WITH_BATTERY_LOW, serialNumber, drone.getBatteryCapacity()));
            } else {
                drone.setState(droneStateDto.getState());
                droneRepository.save(drone);
                DroneDto responseDto = modelMapper.map(drone, DroneDto.class);
                response.addOkResponse200(responseDto);
                return response;
            }
        } catch (LocalNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new LocalBadRequestException(ex.getMessage());
        }
    }

    @Override
    public ApiResponse checkBatteryCapacity(String serialNumber) throws
            LocalBadRequestException, LocalNotFoundException {
        try {
            Drone drone = getDrone(serialNumber);
            DroneBatteryLevelDto responseDto = modelMapper.map(drone, DroneBatteryLevelDto.class);
            response.addOkResponse200(responseDto);
            return response;
        } catch (LocalNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new LocalBadRequestException(ex.getMessage());
        }
    }

    @Override
    public ApiResponse changeBatteryCapacity(String serialNumber, DroneBatteryLevelDto droneInput) throws
            LocalNotFoundException, LocalBadRequestException {
        try {
            Drone drone = getDrone(serialNumber);
            drone.setBatteryCapacity(droneInput.getBatteryCapacity());
            droneRepository.save(drone);
            DroneDto responseDto = modelMapper.map(drone, DroneDto.class);
            response.addOkResponse200(responseDto);
            return response;
        } catch (LocalNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new LocalBadRequestException(ex.getMessage());
        }
    }

    @Override
    public ApiResponse getDronebySerialNumbre(String serialNumber) throws
            LocalNotFoundException, LocalBadRequestException {
        try {
            Drone drone = getDrone(serialNumber);
            DroneDto responseDto = modelMapper.map(drone, DroneDto.class);
            response.addOkResponse200(responseDto);
            return response;
        } catch (LocalNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new LocalBadRequestException(ex.getMessage());
        }
    }

    @Override
    public ApiResponse deleteDrone(String serialNumber) throws LocalNotFoundException, LocalBadRequestException {
        try {
            getDrone(serialNumber);
            droneRepository.deleteById(serialNumber);
            response.addOkResponse200("OK");
            return response;
        } catch (LocalNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new LocalBadRequestException(ex.getMessage());
        }
    }

    @Override
    public ApiResponse getDrones() throws LocalBadRequestException {
        try {
            List<DroneDto> responseDtos = droneRepository.findAll()
                    .stream()
                    .map(e -> modelMapper.map(e, DroneDto.class))
                    .collect(Collectors.toList());
            response.addOkResponse200(responseDtos);
            return response;
        } catch (Exception ex) {
            throw new LocalBadRequestException(ex.getMessage());
        }
    }

    @SneakyThrows
    private Drone getDrone(String serialNumber) throws LocalNotFoundException {
        try {
            Optional<Drone> drone = droneRepository.findById(serialNumber);
            if (drone.isEmpty()) {
                throw new LocalNotFoundException(String.format(MessageText.DRONE_NOT_FOUND, serialNumber));
            }
            return drone.get();
        } catch (LocalNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new LocalBadRequestException(ex.getMessage());
        }
    }

    private List<Drone> getDroneAvailablesForLoading() {
        return droneRepository.findDroneAvailablesForLoading();
    }

    @SneakyThrows
    private Drone getDroneAvailableForLoading(String serialNumber) {
        try {
            Drone drone = getDrone(serialNumber);
            if (drone.getState() != DroneState.IDLE) {
                throw new LocalNotFoundException(String.format(MessageText.DRONE_STATE_NO_READY_TO_FLY_BUSY, serialNumber, drone.getState()));
            }
            if (drone.getBatteryCapacity() < 25) {
                throw new LocalNotFoundException(String.format(MessageText.DRONE_STATE_NO_READY_TO_FLY_BATTERY_LOW, serialNumber, drone.getBatteryCapacity()));
            }
            return drone;
        } catch (LocalNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new LocalBadRequestException(ex.getMessage());
        }
    }
}
