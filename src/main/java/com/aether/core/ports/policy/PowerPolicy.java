package com.aether.core.ports.policy;

import com.aether.core.types.devices.AbstractDevice;

public interface PowerPolicy {

    boolean canTurnOn(AbstractDevice device);

}
