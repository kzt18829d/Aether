package com.aether.adapters.factory;

import com.aether.core.ports.functionalInterfaces.DeviceCreator;
import com.aether.core.types.devices.AbstractDevice;
import com.aether.core.validators.BrightnessValidator;
import com.aether.core.validators.ColorTemperatureValidator;
import com.aether.core.validators.TemperatureValidator;
import com.aether.exceptions.AetherException;
import com.aether.exceptions.DeviceFactoryException;
import com.aether.exceptions.DeviceRegistryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class DeviceFactory {
    private static final Logger logger = LoggerFactory.getLogger(DeviceFactory.class.getName());

    private static volatile DeviceFactory instance;
    private static final Object lock = new Object();

    private static DeviceRegistry deviceRegistry;

    private DeviceFactory() {
        deviceRegistry = DeviceRegistry.getInstance();
        logger.info("Device factory initialized");
    }

    public DeviceFactory getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) instance = new DeviceFactory();
            }
        }
        return instance;
    }

    private void validateRequiredParameters(String deviceType, Map<String, Object> parameters) {
//        Objects.requireNonNull(deviceType, "Device type cannot nbe null");
//        Objects.requireNonNull(parameters, "Device parameters cannot nbe null");

        if (!parameters.containsKey("deviceName") || parameters.get("deviceName") == null)
            throw new IllegalArgumentException("Device name is required");
        if (!parameters.containsKey("deviceLocation") || parameters.get("deviceLocation") == null)
            throw new IllegalArgumentException("Device location is required");

        switch (deviceType) {
            case "Light" -> {
                validateLightParameters(parameters);
                break;
            }
            case "Thermostat" -> {
                validateThermostatParameters(parameters);
                break;
            }
        }
    }

    private void validateLightParameters(Map<String, Object> parameters) {
        if (!parameters.containsKey("deviceUUID")){
            if (!parameters.containsKey("deviceBrightness"))
                parameters.put("deviceBrightness", BrightnessValidator.BASE);
            if (!parameters.containsKey("deviceColorTemperature"))
                parameters.put("deviceColorTemperature", ColorTemperatureValidator.MIN);
        }
    }

    private void validateThermostatParameters(Map<String, Object> parameters) {
        if (!parameters.containsKey("deviceUUID"))
            if (!parameters.containsKey("deviceTemperature"))
                parameters.put("deviceTemperature", TemperatureValidator.BASED);
    }

    private void finalDeviceCheck(AbstractDevice device, String expectedType) {
        Objects.requireNonNull(device, "Created device cannot be null");
        var currentType = device.getClass().getSimpleName();
        if (!expectedType.equals(currentType))
            logger.warn("Type mismatch: expected \"{}\", but got \"{}\"",
                    expectedType, currentType);

        if (device.getDeviceUUID() == null) throw new DeviceFactoryException("Created device has null UUID");
        if (device.getDeviceName() == null) throw new DeviceFactoryException("Created device has null name");
    }

    public Set<String> getSupportedTypes() {
        return deviceRegistry.getRegisteredTypes();
    }

    public boolean isDeviceTypeSupported(String type) {
        return deviceRegistry.contains(type);
    }

    public AbstractDevice createByName(String deviceType, Map<String, Object> parameters) {
        Objects.requireNonNull(deviceType, "Device type cannot nbe null");
        Objects.requireNonNull(parameters, "Device parameters cannot nbe null");

        if (deviceType.trim().isEmpty())
            throw new IllegalArgumentException("Device type name cannot be empty");


        try {
            DeviceCreator creator = deviceRegistry.getRegistry(deviceType);
            validateRequiredParameters(deviceType, parameters);
            AbstractDevice device = creator.create(parameters);
            finalDeviceCheck(device, deviceType);

            logger.info(String.format("Successfully created device of type '%s' with UUID: %s",
                    deviceType, device.getDeviceUUID()));
            return device;

        } catch (DeviceRegistryException exception) {
            logger.error("Failed to create device of type \"{}\": {}", deviceType, exception.getMessage());
            throw new DeviceRegistryException("Unknown device type: " + deviceType +
                    ". Available types: " + deviceRegistry.getRegisteredTypes());
        } catch (Exception e) {
            logger.error("Error creating device of type \"{}\": {}", deviceType, e.getMessage());
            throw new RuntimeException("Device creation failed for type: " + deviceType, e);
        }
    }

    public AbstractDevice createByClass(Class<? extends AbstractDevice> deviceType, Map<String, Object> parameters) {
        Objects.requireNonNull(deviceType, "Device type cannot be null");
        Objects.requireNonNull(parameters, "Parameters map cannot be null");

        String deviceTypeS = deviceType.getSimpleName();
        return createByName(deviceTypeS, parameters);
    }

    public AbstractDevice createRuntime(String deviceType, String deviceName, String deviceLocation) {
        Map<String, Object> parameters = Map.of(
                "deviceName", deviceName,
                "deviceLocation", deviceLocation
        );
        return createByName(deviceType, parameters);
    }

    public AbstractDevice createFromDTO(String deviceType, Map<String, Object> parameters) {
        return createByName(deviceType, parameters);
    }


}
