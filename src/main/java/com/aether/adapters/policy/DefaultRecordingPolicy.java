package com.aether.adapters.policy;

import com.aether.core.ports.policy.RecordingPolicy;
import com.aether.core.types.devices.AbstractDevice;
import com.aether.core.types.enums.DeviceStatus;
import com.aether.core.types.enums.RecordingStatus;
import com.aether.core.types.interfaces.Recording;
import com.aether.exceptions.RecordingPolicyException;

import java.util.Objects;

public class DefaultRecordingPolicy implements RecordingPolicy {
    /**
     * @param device
     * @return
     */
    @Override
    public boolean canRecordingStart(AbstractDevice device) {
        Objects.requireNonNull(device, "Device cannot be null");
        if (!(device instanceof Recording recordObject)) throw new RecordingPolicyException("Device " + device.getDeviceUUID() + " cannot use this policy");
//        Recording recordObject = (Recording) device;
        return device.getDeviceStatus().equals(DeviceStatus.ONLINE) && recordObject.getRecordingStatus().equals(RecordingStatus.DISABLED);
    }

    /**
     * @param device
     * @return
     */
    @Override
    public boolean canRecordingStop(AbstractDevice device) {
        Objects.requireNonNull(device, "Device cannot be null");
        if (!(device instanceof Recording recordObject)) throw new RecordingPolicyException("Device " + device.getDeviceUUID() + " cannot use this policy");
        return device.getDeviceStatus().equals(DeviceStatus.ONLINE) && recordObject.getRecordingStatus().equals(RecordingStatus.ENABLED);
    }
}
