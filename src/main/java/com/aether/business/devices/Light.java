package com.aether.business.devices;

import com.aether.business.types.Brightness;
import com.aether.business.types.ColorTemperature;
import com.aether.business.types.Location;
import com.aether.business.types.Name;

import java.util.Objects;

/**
 * Класс элементов освещения
 *
 * @see Device
 * @see Brightness
 * @see ColorTemperature
 */
public class Light extends Device {
    private final Brightness deviceBrightness;
    private final ColorTemperature deviceColorTemperature;


    /**
     * Конструктор для преобразования из JSON/db
     *
     * @param deviceUUID
     * @param deviceName
     * @param deviceLocation
     * @param deviceStatus
     * @param deviceBrightness
     * @param deviceColorTemperature
     */
    protected Light(String deviceUUID, String deviceName, String deviceLocation, String deviceStatus, int deviceBrightness, int deviceColorTemperature) {
        super(deviceUUID, deviceName, deviceLocation, deviceStatus);
        this.deviceBrightness = new Brightness(deviceBrightness);
        this.deviceColorTemperature = new ColorTemperature(deviceColorTemperature);
    }

    /**
     * Основной конструктор
     *
     * @param deviceName
     * @param deviceLocation
     * @param deviceBrightness
     * @param deviceColorTemperature
     */
    protected Light(Name deviceName, Location deviceLocation, Brightness deviceBrightness, ColorTemperature deviceColorTemperature) {
        super(deviceName, deviceLocation);
        this.deviceBrightness = deviceBrightness;
        this.deviceColorTemperature = deviceColorTemperature;
    }

    /**
     * Конструктор для исключительных случаев
     *
     * @param deviceName
     * @param deviceLocation
     * @param deviceBrightness
     * @param deviceColorTemperature
     */
    protected Light(Name deviceName, Location deviceLocation, int deviceBrightness, int deviceColorTemperature) {
        super(deviceName, deviceLocation);
        this.deviceBrightness = new Brightness(deviceBrightness);
        this.deviceColorTemperature = new ColorTemperature(deviceColorTemperature);
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

    @Override
    public int hashCode() {
        return hashCoder() + getClass().hashCode();
    }
}
