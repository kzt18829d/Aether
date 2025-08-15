package com.aether.core.ports.policy;

import com.aether.core.types.devices.AbstractDevice;

public interface NightVisionPolicy {
    boolean canNvOn(AbstractDevice device);
    boolean canNvOff(AbstractDevice device);
}
