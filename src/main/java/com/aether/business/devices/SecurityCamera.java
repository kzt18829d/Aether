package com.aether.business.devices;

import com.aether.business.devices.SubTypes.Location;
import com.aether.business.devices.SubTypes.Name;
import com.aether.business.enums.Status;

public class SecurityCamera extends Device {
    private boolean isRecording;
    private boolean nightVision;

    public SecurityCamera(Name name, Location location) {
        super(name, location);
        isRecording = false;
        nightVision = false;
    }

    public boolean startRecording() {
        if (status != Status.ONLINE || isRecording) return false;
        isRecording = true;
        return true;
    }

    public boolean stopRecording() {
        if (status != Status.ONLINE || !isRecording) return false;
        isRecording = false;
        return true;
    }

    public boolean enableNightVision() {
        if (status != Status.ONLINE || nightVision) return false;
        nightVision = true;
        return true;
    }

    public boolean disableNightVision() {
        if (status != Status.ONLINE || !nightVision) return false;
        nightVision = false;
        return true;
    }

}
