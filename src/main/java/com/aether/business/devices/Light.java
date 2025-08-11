package com.aether.business.devices;

import com.aether.business.enums.DeviceStatus;
import com.aether.business.types.Brightness;
import com.aether.business.types.ColorTemperature;
import com.aether.business.types.Location;
import com.aether.business.types.Name;
import com.fasterxml.jackson.annotation.*;

import java.util.Objects;
import java.util.UUID;

/**
 * Класс элементов освещения
 *
 * @see Device
 * @see Brightness
 * @see ColorTemperature
 */
@JsonTypeName("Light")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Light extends Device {
    private final Brightness deviceBrightness;
    private final ColorTemperature deviceColorTemperature;


    /**
     * Основной конструктор
     *
     * @param deviceName
     * @param deviceLocation
     * @param deviceBrightness
     * @param deviceColorTemperature
     */
    public Light(Name deviceName, Location deviceLocation, Brightness deviceBrightness, ColorTemperature deviceColorTemperature) {
        super(deviceName, deviceLocation);
        this.deviceBrightness = deviceBrightness;
        this.deviceColorTemperature = deviceColorTemperature;
    }

    /**
     * Конструктор десериализации
     * @param deviceUUID
     * @param deviceName
     * @param deviceLocation
     * @param deviceStatus
     * @param deviceBrightness
     * @param deviceColorTemperature
     */
    @JsonCreator
    public Light(
            @JsonProperty("deviceUUID")             UUID deviceUUID,
            @JsonProperty("deviceName")             Name deviceName,
            @JsonProperty("deviceLocation")         Location deviceLocation,
            @JsonProperty("powerStatus")            DeviceStatus deviceStatus,
            @JsonProperty("deviceBrightness")       Brightness deviceBrightness,
            @JsonProperty("deviceColorTemperature") ColorTemperature deviceColorTemperature) {
        super(deviceUUID, deviceName, deviceLocation, deviceStatus);
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
    public Light(Name deviceName, Location deviceLocation, int deviceBrightness, int deviceColorTemperature) {
        super(deviceName, deviceLocation);
        this.deviceBrightness = new Brightness(deviceBrightness);
        this.deviceColorTemperature = new ColorTemperature(deviceColorTemperature);
    }

    /**
     * Device brightness getter
     * @return Brightness
     */

    @JsonGetter("deviceBrightness")
    public Brightness getDeviceBrightness() {
        return deviceBrightness;
    }

    /**
     * Device Color temperature getter
     * @return ColorTemperature
     */

    @JsonGetter("deviceColorTemperature")
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
    public String getDeviceType() {
        return "Light";
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
