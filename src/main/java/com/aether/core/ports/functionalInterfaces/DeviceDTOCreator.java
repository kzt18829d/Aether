package com.aether.core.ports.functionalInterfaces;

import com.aether.core.types.devices.AbstractDevice;
import com.aether.dto.DeviceDTO;

@FunctionalInterface
public interface DeviceDTOCreator {
    DeviceDTO transform(AbstractDevice device);
}
