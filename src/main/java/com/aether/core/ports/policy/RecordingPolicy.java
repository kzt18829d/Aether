package com.aether.core.ports.policy;

import com.aether.core.types.devices.AbstractDevice;

public interface RecordingPolicy {
    boolean canRecordingStart(AbstractDevice device);
    boolean canRecordingStop(AbstractDevice device);
}
