package com.aether.core.ports.functionalInterfaces;

import com.aether.core.types.devices.AbstractDevice;

import java.util.Map;

@FunctionalInterface
public interface DeviceCreator {
    AbstractDevice create(Map<String, Object> parameters);
}
