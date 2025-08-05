package com.aether.business.devices;

import com.aether.business.devices.deviceInterfaces.Lockable;
import com.aether.business.enums.DeviceStatus;
import com.aether.business.enums.LockStatus;
import com.aether.business.types.Location;
import com.aether.business.types.Name;

public class SmartLock extends Device implements Lockable {
    private LockStatus deviceLockStatus;
    /**
     * Конструктор для преобразования из JSON/db
     *
     * @param deviceUUID
     * @param deviceName
     * @param deviceLocation
     * @param deviceStatus
     */
    protected SmartLock(String deviceUUID, String deviceName, String deviceLocation, String deviceStatus) {
        super(deviceUUID, deviceName, deviceLocation, deviceStatus);
        this.deviceLockStatus = LockStatus.UNLOCKED;
    }

    /**
     * Основной конструктор
     *
     * @param deviceName
     * @param deviceLocation
     */
    protected SmartLock(Name deviceName, Location deviceLocation) {
        super(deviceName, deviceLocation);
        this.deviceLockStatus = LockStatus.UNLOCKED;
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
