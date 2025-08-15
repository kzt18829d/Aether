package com.aether.core.types.devices;

import com.aether.core.types.enums.DeviceStatus;
import com.aether.core.types.enums.LockStatus;
import com.aether.core.types.interfaces.Lockable;
import com.aether.core.types.wrappers.Location;
import com.aether.core.types.wrappers.Name;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class SmartLock extends AbstractDevice implements Lockable {
    protected AtomicReference<LockStatus> deviceLockStatus;

    /**
     * Основной конструктор
     *
     * @param deviceName
     * @param deviceLocation
     */
    public SmartLock(Name deviceName, Location deviceLocation) {
        super(deviceName, deviceLocation);
        this.deviceLockStatus = new AtomicReference<>(LockStatus.UNLOCKED);
    }

    /**
     * Deserialization Constructor
     * @param deviceUUID
     * @param deviceName
     * @param deviceLocation
     * @param deviceStatus
     * @param deviceLockStatus
     */
    public SmartLock(
            UUID deviceUUID,
            Name deviceName,
            Location deviceLocation,
            DeviceStatus deviceStatus,
            LockStatus deviceLockStatus) {
        super(deviceUUID, deviceName, deviceLocation, deviceStatus);
        this.deviceLockStatus = new AtomicReference<>(deviceLockStatus);
    }

    /**
     * Заблокировать.
     * True - был заблокирован, False - не был заблокирован
     *
     * @return boolean
     */
    @Override
    public boolean lock() {
        deviceLockStatus.set(LockStatus.LOCKED);
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
        deviceLockStatus.set(LockStatus.UNLOCKED);
        return true;
    }

    /**
     * Статус блокировки
     *
     * @return boolean
     */
    @Override
    public boolean getLockStatusBoolean() {
        return LockStatus.getBoolean(deviceLockStatus.get());
    }

    public LockStatus getLockStatus() {
        return deviceLockStatus.get();
    }

}
