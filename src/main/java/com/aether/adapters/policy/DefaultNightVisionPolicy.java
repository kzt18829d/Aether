package com.aether.adapters.policy;

import com.aether.core.ports.policy.NightVisionPolicy;
import com.aether.core.types.devices.AbstractDevice;
import com.aether.core.types.enums.DeviceStatus;
import com.aether.core.types.enums.NightVisionStatus;
import com.aether.core.types.interfaces.NightVision;
import com.aether.exceptions.NightVisionException;

import java.util.Objects;

public class DefaultNightVisionPolicy implements NightVisionPolicy {
    /**
     * @param device
     * @return
     */
    @Override
    public boolean canNvOn(AbstractDevice device) {
        Objects.requireNonNull(device, "Device cannot be null");
        if (!(device instanceof NightVision visionObject)) throw new NightVisionException("Device " + device.getDeviceUUID() +
                " cannot use this night vision policy");
        return device.getDeviceStatus().equals(DeviceStatus.ONLINE) && visionObject.getNightVisionStatus().equals(NightVisionStatus.DISABLED);
    }

    /**
     * @param device
     * @return
     */
    @Override
    public boolean canNvOff(AbstractDevice device) {
        Objects.requireNonNull(device, "Device cannot be null");
        if (!(device instanceof NightVision visionObject)) throw new NightVisionException("Device " + device.getDeviceUUID() +
                " cannot use this night vision policy");
        return device.getDeviceStatus().equals(DeviceStatus.ONLINE) && visionObject.getNightVisionStatus().equals(NightVisionStatus.ENABLED);
    }
}
