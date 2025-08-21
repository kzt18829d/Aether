package com.aether.services;

import com.aether.adapters.factory.DeviceDTOFactory;
import com.aether.adapters.factory.DeviceFactory;
import com.aether.core.ports.services.DevicesDataTransferObjectService;
import com.aether.core.types.devices.AbstractDevice;
import com.aether.dto.*;
import com.aether.exceptions.DeviceFactoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import static com.aether.core.logging.LoggerMarkers.*;

public class DevicesDataTransferObjectServiceImpl implements DevicesDataTransferObjectService {
    private static final Logger logger = LoggerFactory.getLogger(DevicesDataTransferObjectServiceImpl.class);
    private final DeviceFactory deviceFactory;
    private final DeviceDTOFactory deviceDTOFactory;

    public DevicesDataTransferObjectServiceImpl() {
        this.deviceFactory = DeviceFactory.getInstance();
        this.deviceDTOFactory = DeviceDTOFactory.getInstance();
        logger.info("DevicesDataTransferObjectService initialized");
    }

    public DevicesDataTransferObjectServiceImpl(DeviceFactory deviceFactory, DeviceDTOFactory deviceDTOFactory) {
        Objects.requireNonNull(deviceFactory, "DeviceFactory cannot be null");
        Objects.requireNonNull(deviceDTOFactory, "DeviceDTOFactory cannot be null");
        this.deviceFactory = deviceFactory;
        this.deviceDTOFactory = deviceDTOFactory;
        logger.info("DevicesDataTransferObjectService initialized with custom factories");
    }


    private String identifyDeviceType(DeviceDTO deviceDTO) {
        String dtoClass = deviceDTO.getClass().getSimpleName();

        if (!dtoClass.endsWith("DTO"))
            throw new IllegalArgumentException("DTO class name must end with 'DTO': " + dtoClass);

        String className = dtoClass.substring(0, dtoClass.length() - 3);
        if (!deviceFactory.isDeviceTypeSupported(className))
            throw new IllegalArgumentException("Unsupported device type: " + className +
                        ". Supported types: " + deviceFactory.getSupportedTypes());

        return className;
    }

    private Map<String, Object> extractParametersFromDTO(DeviceDTO deviceDTO) {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("deviceUUID", deviceDTO.getDeviceUUID());
        parameters.put("deviceName", deviceDTO.getDeviceName());
        parameters.put("deviceLocation", deviceDTO.getDeviceLocation());
        parameters.put("deviceStatus", deviceDTO.getDeviceStatus());

        switch (deviceDTO) {
            case LightDTO lightDTO : {
                parameters.put("deviceBrightness", lightDTO.getDeviceBrightness());
                parameters.put("deviceColorTemperature", lightDTO.getDeviceColorTemperature());
                break;
            }
            case SecurityCameraDTO securityCameraDTO : {
                parameters.put("deviceRecordingStatus", securityCameraDTO.getDeviceRecordingStatus());
                parameters.put("deviceNightVisionStatus", securityCameraDTO.getDeviceNightVisionStatus());
                break;
            }
            case SmartLockDTO smartLockDTO : {
                parameters.put("deviceLockStatus", smartLockDTO.getDeviceLockStatus());
                break;
            }
            case ThermostatDTO thermostatDTO : {
                parameters.put("deviceTemperature", thermostatDTO.getDeviceTemperature());
                break;
            }
            default: throw new IllegalArgumentException("Unsupported DTO type: " + deviceDTO.getClass().getSimpleName());
        }
        return parameters;
    }

    public Set<String> getSupportedDeviceTypes() {
        var deviceTypes = deviceFactory.getSupportedTypes();
        var dtoTypes = deviceDTOFactory.getSupportedDevices();
        return deviceTypes.stream().filter(deviceDTOFactory::isDeviceTypeSupported).collect(Collectors.toSet());
    }

    /**
     * @param deviceDTOList
     * @return
     */
    @Override
    public List<AbstractDevice> deserializeFromDTO(List<DeviceDTO> deviceDTOList) {
        Objects.requireNonNull(deviceDTOList, "DeviceDTO list cannot be null");
        if (deviceDTOList.isEmpty()) {
            logger.info("Empty DTO list provided for deserialization");
            return new ArrayList<>();
        }

        List<AbstractDevice> deviceList = new ArrayList<>(20);
        int totalCount = 0;
        int successCount = 0;
        int errorCount = 0;

        for (DeviceDTO deviceDTO : deviceDTOList) {
            if (deviceDTO == null) {
                logger.warn("Null DTO encountered in list, skipping");
                totalCount++;
                errorCount++;
                continue;
            }

            try {
                AbstractDevice abstractDevice = deserializeFromDTO(deviceDTO);
                deviceList.add(abstractDevice);
                successCount++;
            } catch (Exception e) {
                errorCount++;
                logger.warn("Failed to deserialize DTO of type {} with UUID {}: {}", deviceDTO.getClass().getSimpleName(), deviceDTO.getDeviceUUID(), e.getMessage());
            } finally {
                totalCount++;
            }
        }
        logger.info("Load completed: Total: {}, Successful: {}, Error: {}", totalCount, successCount, errorCount);
        return deviceList;
    }

    /**
     * @param deviceDTO
     * @return
     */
    @Override
    public AbstractDevice deserializeFromDTO(DeviceDTO deviceDTO) {
        Objects.requireNonNull(deviceDTO, "Device Data Transfer Object cannot be null");
        try {
            var deviceType = identifyDeviceType(deviceDTO);
            var parameters = extractParametersFromDTO(deviceDTO);
            AbstractDevice device = deviceFactory.createFromDTO(deviceType, parameters);
            logger.info("Successfully deserialized {} with UUID: {}", deviceType, device.getDeviceUUID());
            return device;
        } catch (Exception e) {
            logger.error("Failed to deserialize DTO of type {}: {}", deviceDTO.getClass().getSimpleName(), e.getMessage());
            throw e;
        }
    }

    /**
     * @param device
     * @return
     */
    @Override
    public DeviceDTO serializeToDTO(AbstractDevice device) {
        Objects.requireNonNull(device, "Device cannot be null");

        try {
            DeviceDTO deviceDTO = deviceDTOFactory.createDTO(device);
            logger.info("Successfully serialized {} with UUID: {} to DTO", device.getClass().getSimpleName(), device.getDeviceUUID());
            return deviceDTO;
        } catch (Exception e) {
            logger.error("Failed to serialize device of type {} with UUID {}: {}", device.getClass().getSimpleName(), device.getDeviceUUID(), e.getMessage());
            throw e;
        }
    }

    /**
     * @param deviceList
     * @return
     */
    @Override
    public List<DeviceDTO> serializeToDTO(List<AbstractDevice> deviceList) {
        Objects.requireNonNull(deviceList, "Device list cannot be null");
        if (deviceList.isEmpty()) {
            logger.info("Empty devices list provided for serialization");
            return new ArrayList<>();
        }

        List<DeviceDTO> deviceDTOList = new ArrayList<>();
        int totalCount = 0;
        int successCount = 0;
        int errorCount = 0;
        for (AbstractDevice abstractDevice : deviceList) {
            try {
                DeviceDTO deviceDTO = serializeToDTO(abstractDevice);
                deviceDTOList.add(deviceDTO);
                successCount++;
            } catch (Exception e) {
                logger.error("Failed to serialize device of type {} with UUID {}: {}", abstractDevice.getClass().getSimpleName(), abstractDevice.getDeviceUUID(), e.getMessage());
            } finally {
                totalCount++;
            }
        }

        logger.info("Serialization completed: Total: {}, Successful: {}, Error: {}", totalCount, successCount, errorCount);
        return deviceDTOList;
    }
}
