package com.aether.adapters.factory;

import com.aether.core.ports.functionalInterfaces.DeviceCreator;
import com.aether.core.types.devices.AbstractDevice;

import java.util.Map;

public class DeviceFactory {
    private static final DeviceFactory instance = new DeviceFactory();
    private final DeviceRegistry deviceRegistry = new DeviceRegistry();

    public AbstractDevice create(Class<? extends AbstractDevice> type, Map<String, Object> parameters) {
        return deviceRegistry.getRegistry(type.getSimpleName()).create(parameters);
    }
}
