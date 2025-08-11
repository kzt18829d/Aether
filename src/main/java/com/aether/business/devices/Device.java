package com.aether.business.devices;

import com.aether.business.Exceptions.valid.DeviceException;
import com.aether.business.devices.deviceInterfaces.Relocation;
import com.aether.business.devices.deviceInterfaces.TurnPower;
import com.aether.business.enums.DeviceStatus;
import com.aether.business.types.Location;
import com.aether.business.types.Name;
import com.fasterxml.jackson.annotation.*;

import java.util.Objects;
import java.util.UUID;

/**
 * Абстрактный класс Device, родительский класс устройств умного дома.
 * @see Name
 * @see Location
 * @see DeviceStatus
 * @see TurnPower
 * @see Relocation
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "deviceType",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Light.class, name = "Light"),
        @JsonSubTypes.Type(value = SecurityCamera.class, name = "SecurityCamera"),
        @JsonSubTypes.Type(value = SmartLock.class, name = "SmartLock"),
        @JsonSubTypes.Type(value = Thermostat.class, name = "Thermostat")
}
)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Device implements TurnPower<DeviceStatus>, Relocation {
    /**
     * UUID устройства
     * Название устройства
     * Местоположение устройства
     * Статус устройства
     */
    private final UUID deviceUUID;
    private final Name deviceName;
    private Location deviceLocation;
    DeviceStatus deviceStatus;


    /**
     * Основной конструктор
     * @param deviceName
     * @param deviceLocation
     */
    protected Device(Name deviceName, Location deviceLocation) {
        this.deviceUUID = UUID.randomUUID();
        this.deviceName = deviceName;
        this.deviceLocation = deviceLocation;
        this.deviceStatus = DeviceStatus.ONLINE;
    }

    /**
     * Конструктор десериализации
     * @param deviceUUID
     * @param deviceName
     * @param deviceLocation
     * @param deviceStatus
     */
    @JsonCreator
    protected Device(
            @JsonProperty("deviceUUID")     UUID deviceUUID,
            @JsonProperty("deviceName")     Name deviceName,
            @JsonProperty("deviceLocation") Location deviceLocation,
            @JsonProperty("powerStatus")   DeviceStatus deviceStatus) {
        this.deviceUUID = deviceUUID;
        this.deviceName = deviceName;
        this.deviceLocation = deviceLocation;
        this.deviceStatus = deviceStatus;
    }

    //------------------------------ Class/Wrapper-class getters ------------------------------------------------------------------------------------------------------

    /**
     * Device name getter
     * @return Name
     */
    public final Name getDeviceName() { return deviceName; }
//    public final String getDeviceName() { return deviceName.getString(); }

    /**
     * Device location getter
     * @return Location
     */
    public final Location getDeviceLocation() { return deviceLocation; }

    /**
     * Device UUID getter
     * @return UUID
     */
    public final UUID getDeviceUUID() { return deviceUUID; }

    /**
     * Статус питания
     * @return DeviceStatus
     */
    public DeviceStatus getPowerStatus() {
        return this.deviceStatus;
    }

    //------------------------------ To String getters ------------------------------------------------------------------------------------------------------

    /**
     * Device location getter (to String)
     * @return String
     */
    @JsonGetter("deviceLocation")
    public final String getDeviceLocation_string() { return deviceLocation.getString(); }

    /**
     * Device name getter (to String)
     * <p>!JsonGetter</p>
     * @return String
     */
    @JsonGetter("deviceName")
    public final String getDeviceName_string() { return deviceName.getString(); }

    /**
     * Device UUID getter (to String)
     * <p>!JsonGetter</p>
     * @return String
     */
    @JsonGetter("deviceUUID")
    public final String getDeviceUUID_string() { return deviceUUID.toString(); }

    /**
     * Device type getter
     * <p>!JsonGetter</p>
     * @return String
     */
    @JsonGetter("deviceType")
    public String getDeviceType() {
        return "Device";
    }

    //------------------------------ Logic methods ------------------------------------------------------------------------------------------------------

    /**
     * Device location setter
     * @param deviceLocation
     */
    public void setDeviceLocation(Location deviceLocation) {
        if (this.deviceLocation != null) throw new DeviceException("The device is already linked to the location");
        this.deviceLocation = deviceLocation;
    }

    /**
     * Device location deleter
     */
    public void removeDeviceLocation() {
        deviceLocation = null;
    }

    /**
     * Включить устройство
     * @return boolean
     */
    @Override
    public boolean turnOn() {
        if (deviceLocation == null) return false;
        try {
            deviceStatus = DeviceStatus.ONLINE;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Выключить устройство
     * @return boolean
     */
    @Override
    public final boolean turnOff() {
        try{
            deviceStatus = DeviceStatus.OFFLINE;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Релокация устройства
     * @param location
     */
    @Override
    public void relocate(Location location) {
        try {
            this.deviceLocation = location;
        } catch (Exception e) {
            throw new DeviceException("Device " + deviceUUID + " wasn't relocated");
        }
    }

    //------------------------------ Hash&Equals methods (Java core) ------------------------------------------------------------------------------------------------------


    /**
     * Вспомогательный инструмент для генерации хэш-функций
     * @return int
     */
    protected int hashCoder() {
        return 31 * deviceUUID.hashCode() + deviceName.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return Objects.equals(deviceUUID, device.deviceUUID) && Objects.equals(deviceName, device.deviceName) && Objects.equals(deviceLocation, device.deviceLocation) && deviceStatus == device.deviceStatus;
    }

    @Override
    public int hashCode() {
        return hashCoder() + getClass().hashCode();
    }
}
