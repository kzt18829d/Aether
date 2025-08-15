package com.aether.core.ports.services;

import java.util.UUID;

public interface DeviceControlService {

    boolean turnStatusOn(UUID deviceUUID);
    boolean turnStatusOff(UUID deviceUUID);


}
