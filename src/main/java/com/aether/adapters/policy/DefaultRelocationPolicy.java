package com.aether.adapters.policy;

import com.aether.core.ports.policy.RelocationPolicy;
import com.aether.core.types.devices.AbstractDevice;
import com.aether.core.types.wrappers.Location;
import com.aether.exceptions.RelocationPolicyException;

import java.util.Objects;

public class DefaultRelocationPolicy implements RelocationPolicy {
    /**
     * @param device
     * @param location
     */
    @Override
    public void assertLink(AbstractDevice device, Location location) {
        Objects.requireNonNull(device, "Device cannot be null");
        Objects.requireNonNull(location, "Location cannot be null");
        if (device.getLocation() != null) throw new RelocationPolicyException("Device was linked to location early. Link failed");
    }

    /**
     * @param device
     * @param location
     */
    @Override
    public void assertRelocate(AbstractDevice device, Location location) {
        Objects.requireNonNull(device, "Device cannot be null");
        Objects.requireNonNull(location, "Location cannot be null");
        if (device.getLocation() == null) throw new RelocationPolicyException("Device doesn't link to something location. Relocation failed");
    }
}
