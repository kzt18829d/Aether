package com.aether.business.devices;

import com.aether.business.types.Location;
import com.aether.business.types.Name;
import com.aether.business.enums.Status;

public class SmartLock extends Device {
    private boolean isLocked;

    public SmartLock(Name name, Location location) {
        super(name, location);
        this.isLocked = false;
    }

    public boolean getLockStatus() {
        return isLocked;
    }

    public boolean lock() {
        if (status != Status.ONLINE) return false;
        this.isLocked = true;
        return true;
    }

    public boolean unlock() {
        if (status != Status.ONLINE) return false;
        this.isLocked = false;
        return true;
    }

    @Override
    public String getType() {
        return "SmartLock";
    }
}
