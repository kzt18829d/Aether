package com.aether.core.ports.policy;

import com.aether.core.types.devices.AbstractDevice;
import com.aether.core.types.wrappers.Location;

public interface RelocationPolicy {
    void assertLink(AbstractDevice device, Location location);
    void assertRelocate(AbstractDevice device, Location location);
}
