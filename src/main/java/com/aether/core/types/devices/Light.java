package com.aether.core.types.devices;

import com.aether.core.types.enums.DeviceStatus;
import com.aether.core.types.wrappers.Brightness;
import com.aether.core.types.wrappers.ColorTemperature;
import com.aether.core.types.wrappers.Location;
import com.aether.core.types.wrappers.Name;

import java.util.Objects;
import java.util.UUID;

public class Light extends AbstractDevice {
    protected final Brightness deviceBrightness;
    protected final ColorTemperature deviceColorTemperature;
    /**
     * Main Constructor
     *
     * @param deviceName     Name of device
     * @param deviceLocation Device location
     * @param deviceBrightness Device brightness
     * @param deviceColorTemperature Device color temperature
     * @see Name
     * @see Location
     */
    public Light(Name deviceName, Location deviceLocation, Brightness deviceBrightness, ColorTemperature deviceColorTemperature) {
        super(deviceName, deviceLocation);
        this.deviceBrightness = deviceBrightness;
        this.deviceColorTemperature = deviceColorTemperature;
    }

    /**
     * Serialization from DTO objects Constructor
     *
     * @param deviceUUID     Device UUID
     * @param deviceName     Device name
     * @param deviceLocation Device location
     * @param deviceStatus   Device current Status
     * @param deviceBrightness Device brightness
     * @param deviceColorTemperature Device color temperature
     */
    public Light(
            UUID deviceUUID,
            Name deviceName,
            Location deviceLocation,
            DeviceStatus deviceStatus,
            Brightness deviceBrightness,
            ColorTemperature deviceColorTemperature) {
        super(deviceUUID, deviceName, deviceLocation, deviceStatus);
        this.deviceBrightness = deviceBrightness;
        this.deviceColorTemperature = deviceColorTemperature;
    }

    /**
     * Device brightness getter
     * @return Brightness
     */
    public Brightness getDeviceBrightness() {
        return deviceBrightness;
    }

    /**
     * Device Color temperature getter
     * @return ColorTemperature
     */
    public ColorTemperature getDeviceColorTemperature() {
        return deviceColorTemperature;
    }

    /**
     * Device brightness setter
     * @param newBrightness
     */
    public void setDeviceBrightness(Integer newBrightness) {
        this.deviceBrightness.setBrightness(newBrightness);
    }

    /**
     * Device color temperature setter
     * @param newColorTemperature
     */
    public void setDeviceColorTemperature(Integer newColorTemperature) {
        this.deviceColorTemperature.setTemperature(newColorTemperature);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Light light = (Light) o;
        return Objects.equals(deviceBrightness, light.deviceBrightness) && Objects.equals(deviceColorTemperature, light.deviceColorTemperature);
    }
}
