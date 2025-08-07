package com.aether.business.devices;

import com.aether.business.devices.deviceInterfaces.Lockable;
import com.aether.business.enums.DeviceStatus;
import com.aether.business.enums.LockStatus;
import com.aether.business.types.Location;
import com.aether.business.types.Name;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.UUID;

@JsonTypeName("SmartLock")
public class SmartLock extends Device implements Lockable {
    private LockStatus deviceLockStatus;


    /**
     * Основной конструктор
     *
     * @param deviceName
     * @param deviceLocation
     */
    public SmartLock(Name deviceName, Location deviceLocation) {
        super(deviceName, deviceLocation);
        this.deviceLockStatus = LockStatus.UNLOCKED;
    }

    /**
     * Конструктор десериализации
     * @param deviceUUID
     * @param deviceName
     * @param deviceLocation
     * @param deviceStatus
     * @param deviceLockStatus
     */
    @JsonCreator
    public SmartLock(
            @JsonProperty("deviceUUID") UUID deviceUUID,
            @JsonProperty("deviceName") Name deviceName,
            @JsonProperty("deviceLocation") Location deviceLocation,
            @JsonProperty("deviceStatus") DeviceStatus deviceStatus,
            @JsonProperty("deviceLockStatus") LockStatus deviceLockStatus) {
        super(deviceUUID, deviceName, deviceLocation, deviceStatus);
        this.deviceLockStatus = deviceLockStatus;
    }

    /**
     * Заблокировать.
     * True - был заблокирован, False - не был заблокирован
     *
     * @return boolean
     */
    @Override
    public boolean lock() {
        if (deviceStatus != DeviceStatus.ONLINE) return false;
        this.deviceLockStatus = LockStatus.LOCKED;
        return true;
    }

    /**
     * Разблокировать.
     * True - был разблокирован, False - не был разблокирован
     *
     * @return boolean
     */
    @Override
    public boolean unlock() {
        if (deviceStatus != DeviceStatus.ONLINE) return false;
        this.deviceLockStatus = LockStatus.UNLOCKED;
        return true;
    }

    /**
     * Статус блокировки
     *
     * @return boolean
     */
    @Override
    public boolean getLockStatusBoolean() {
        return LockStatus.getBoolean(this.deviceLockStatus);
    }

    /**
     * Статус блокировки
     *
     * @return String
     */
    @JsonGetter("deviceLockStatus")
    @Override
    public String getLockStatusString() {
        return LockStatus.getString(this.deviceLockStatus);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SmartLock smartLock = (SmartLock) o;
        return deviceLockStatus == smartLock.deviceLockStatus;
    }

    @Override
    public int hashCode() {
        return hashCoder() + getClass().hashCode();
    }
}
